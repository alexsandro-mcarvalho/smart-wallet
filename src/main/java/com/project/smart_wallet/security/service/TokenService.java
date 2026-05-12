package com.project.smart_wallet.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.smart_wallet.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {
    @Value("${JWT_SECRET}")
    private String secret;


    public TokenResponse generateToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Instant expiresAt = generateExpirationTime();
        String token = JWT.create()
                .withIssuer("smart-wallet")
                .withSubject(user.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new TokenResponse(token, getExpirationInSeconds(expiresAt));
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("smart-wallet")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationTime() {
        return Instant.now().plus(Duration.ofHours(2));
    }

    private long getExpirationInSeconds(Instant expiresAt) {
        return Duration.between(Instant.now(), expiresAt).getSeconds();
    }

}
