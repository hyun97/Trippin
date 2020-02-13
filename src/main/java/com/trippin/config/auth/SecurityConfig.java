package com.trippin.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // TODO: 접근권한 수정
                .antMatchers(
                        "/",
                        "/css/**",
                        "/img/**",
                        "/js/**",
                        "/h2-console/**",
                        "/user/**",
                        "/country/**",
                        "/bookmark/**",
                        "/api/**"
                ).permitAll()
//                .antMatchers(
//                        "/api/**"
//                ).hasRole(Role.USER.name())
//                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}