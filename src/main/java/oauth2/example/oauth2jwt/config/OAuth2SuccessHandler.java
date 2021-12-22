package oauth2.example.oauth2jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oauth2.example.oauth2jwt.domain.Role;
import oauth2.example.oauth2jwt.domain.Token;
import oauth2.example.oauth2jwt.domain.User;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Token token = jwtUtils.generateToken(oAuth2User.getAttribute("login"), Role.USER.getKey());
        log.info("jwt = {}",token.getToken());
        log.info("refresh jwt = {}",token.getRefreshToken());
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token.getToken())
//                .domain("42cadet.kr")
                .sameSite("Lax")
                .path("/")
                .httpOnly(false)
                .maxAge(-1)
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh", token.getToken())
//                .domain("42cadet.kr")
                .sameSite("Lax")
                .path("/")
                .httpOnly(false)
                .maxAge(-1)
                .build();
        response.addHeader("Set-cookie",jwtCookie.toString());
        response.addHeader("Set-cookie",refreshCookie.toString());
    }
}
