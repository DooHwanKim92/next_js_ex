package com.rest.proj.global.security;


import com.rest.proj.global.security.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/*/articles").permitAll()
                                .requestMatchers("/api/*/articles/*").permitAll()
                                // .requestMatchers(HttpMethod.POST,"/api/*/members/login").permitAll()
                                .requestMatchers("/api/*/members/login").permitAll()
                                .anyRequest().authenticated()
                )
                .cors(
                        cors -> cors.disable()
                )
                .csrf(
                        csrf -> csrf.disable()
                )
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                )
                .formLogin(
                        formLogin -> formLogin.disable()
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS)
                )
                .addFilterBefore(
                        jwtAuthorizationFilter, //엑세스 토큰을 이용한 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                )
        ;
        return http.build();
    }
}