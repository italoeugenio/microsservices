package com.ms.stock_control_api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private Middleware middleware;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/api/v1/brand/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/brand/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/brand/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/watch/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/watch/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/watch/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(middleware, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}