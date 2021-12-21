package oauth2.example.oauth2jwt.config;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
@Component
public class JwtFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tmp = ((HttpServletRequest) request).getRequestURI().toString();
        if (tmp.equals("/")) {
            return;
        }
        else {
            System.out.println("Filter");
        }
    }
}
