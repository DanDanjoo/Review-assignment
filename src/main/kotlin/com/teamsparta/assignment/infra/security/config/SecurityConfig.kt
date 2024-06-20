package com.teamsparta.assignment.infra.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic{it.disable()} // HTTP 기본 인증을 비활성화!
            .formLogin{it.disable()} // 폼 로그인 방식을 비활성화!
            .csrf{it.disable()} // CSRF 보호를 비활성화!
            .authorizeHttpRequests{
                it
                    .requestMatchers("/swagger-ui/**").permitAll() // /swagger-ui/** 경로는 모두 접근 허용
                    .requestMatchers("/v3/api-docs/**").permitAll() // /v3/api-docs/** 경로는 모두 접근 허용
                    .anyRequest().authenticated()  // 그 외 모든 요청은 인증해야함
            }
            .build()
    }
}