package com.teamsparta.assignment.infra.security.config

import com.teamsparta.assignment.infra.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

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
                    .requestMatchers(HttpMethod.POST, "/api/v1/members/signup").permitAll() // 회원 가입 경로 모두 접근 허용
                    .requestMatchers(HttpMethod.POST, "/api/v1/members/login").permitAll() // 로그인 경로 모두 접근 허용
                    .anyRequest().authenticated()  // 그 외 모든 요청은 인증해야함

            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            // requestMatchers를 통해 인증, 허용된 URL 외 다른요청이 들어왔을 때 사용함
            .build()
    }
}