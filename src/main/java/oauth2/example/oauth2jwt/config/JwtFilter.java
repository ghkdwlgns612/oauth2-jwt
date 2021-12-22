package oauth2.example.oauth2jwt.config;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter
@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtils jwtUtils;
    private final CustomOAuth2UserService customOAuth2UserService;
//액세스토큰이 실패되면 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tmp = ((HttpServletRequest) request).getRequestURI();
        System.out.println("Filter입장 : " + tmp);
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies == null) {
            System.out.println("쿠키가없음");
            return; //쿠키가 없는경우(프론트처리)
        }
        Optional<Cookie> jwt = Arrays.stream(((HttpServletRequest) request).getCookies())
                                .filter(c -> c.getName().equals("jwt"))
                                .findFirst();
        Optional<Cookie> refresh = Arrays.stream(((HttpServletRequest) request).getCookies())
                                .filter(c -> c.getName().equals("refresh"))
                                .findFirst();
        if (refresh.isEmpty()) {
            System.out.println("refresh없음");
            return; //refresh가 없는경우(프론트처리)
        }
        if (!jwtUtils.verifyToken(refresh.get().getValue())) {
            //Oauth2재실행(프론트처리,쿠키를 삭제)
            return;
        } else {
            if (!jwtUtils.verifyToken(refresh.get().getValue())) {
                //JWT재발급
                System.out.println("JWT토큰재발급");
                String username = jwtUtils.getUid(jwt.get().getValue());
                String reToken = jwtUtils.generateJwtToken(username);
                ResponseCookie jwtCookie = ResponseCookie.from("jwt", reToken)
//                .domain("42cadet.kr")
                        .sameSite("Lax")
                        .path("/")
                        .httpOnly(false)
                        .maxAge(-1)
                        .build();
                System.out.println("username : " + username);
                System.out.println("retoken : " + reToken);
                ((HttpServletResponse) response).addHeader("Set-cookie", jwtCookie.toString());
            } else {
                //Oauth실행
                System.out.println("둘다 양호");
                if (tmp.equals("/user")) {
                    System.out.println("userFilter");
                } else if (tmp.equals("/admin")) {
                    System.out.println("adminFilter");
                } else {
                    System.out.println("generalFilter");
                }
                return;
            }
        }
        chain.doFilter(request,response);
    }
}
