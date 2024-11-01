package com.ssafy.TripHub.global.auth;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Aspect
@Component
public class AuthAspect {
    private final HttpSession httpSession;
    public static final String USER_SESSION = "user";

    public AuthAspect(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Before("@annotation(Authenticated)")
    public void checkAuthentication() {
        if (Objects.isNull(httpSession) || Objects.isNull(httpSession.getAttribute(USER_SESSION))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
    }
}
