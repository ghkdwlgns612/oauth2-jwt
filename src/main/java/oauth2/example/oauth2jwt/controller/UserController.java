package oauth2.example.oauth2jwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/")
    public void test1() {
        log.info("test1");
    }

    @GetMapping("/login/callback/42")
    public void test2(@RequestParam String accessToken) {
        log.info("access = {}", accessToken);
        log.info("redirect");
    }

    @GetMapping("/user")
    public void test3() {
        log.info("user");
    }

    @GetMapping("/oauth2/authorization/intra42")
    public void test4() {
        log.info("왔다");
    }
}
