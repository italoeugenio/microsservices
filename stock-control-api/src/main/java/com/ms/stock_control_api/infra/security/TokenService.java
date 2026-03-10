package com.ms.stock_control_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-user")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    public List<SimpleGrantedAuthority> getRoles(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        DecodedJWT decoded = JWT.require(algorithm)
                .withIssuer("api-user")
                .build()
                .verify(token);

        String role = decoded.getClaim("role").asString();
        if (role.equals("ROLE_ADMIN")) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_CLIENT")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }
}
