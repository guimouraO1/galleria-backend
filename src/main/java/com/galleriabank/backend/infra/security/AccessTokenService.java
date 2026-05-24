package com.galleriabank.backend.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.galleriabank.backend.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class AccessTokenService {

    @Value("${api.security.token.public}")
    private RSAPublicKey publicKey;

    @Value("${api.security.token.private}")
    private RSAPrivateKey secretKey;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, secretKey);

            return JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("login", user.getLogin())
                    .withExpiresAt(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2).toInstant())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token");
        }
    }

    public Long validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            String subject = JWT.require(algorithm)
                    .build().verify(token).getSubject();

            return Long.valueOf(subject);

        } catch (JWTVerificationException | NumberFormatException exception) {
            return null;
        }
    }
}
