package oauth2.example.oauth2jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
public class Oauth2JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2JwtApplication.class, args);
	}

}
