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
                        .requestMatchers(HttpMethod.GET, "/api/v1/brand/**").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/watch/**").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/brand/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/watch/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/brand/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/watch/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/brand/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/watch/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/server").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(middleware, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}