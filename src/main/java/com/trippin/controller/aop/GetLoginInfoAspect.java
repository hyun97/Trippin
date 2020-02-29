package com.trippin.controller.aop;

import com.trippin.config.auth.SessionUser;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class GetLoginInfoAspect {

    private final UserRepository userRepository;

    @Around("@annotation(GetLoginInfo)")
    public Object loginInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        Model model = Arrays.stream(joinPoint.getArgs())
                .filter(Model.class::isInstance)
                .map(Model.class::cast)
                .findFirst()
                .orElse(null);

        SessionUser user = Arrays.stream(joinPoint.getArgs())
                .filter(SessionUser.class::isInstance)
                .map(SessionUser.class::cast)
                .findFirst()
                .orElse(null);

        Long userId = Arrays.stream(joinPoint.getArgs())
                .filter(Long.class::isInstance)
                .map(Long.class::cast)
                .findFirst()
                .orElse(null);

        if (user != null) {
            model.addAttribute("loginUser", userRepository.getOne(user.getId()));
            model.addAttribute("isLogin", true);

            if (userId != null && userId.equals(user.getId())) {
                model.addAttribute("validUser", true);
            }
        }

        Object proceed = joinPoint.proceed();

        return proceed;
    }

}
