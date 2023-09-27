package ru.castroy10.security_mongo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${secret}")
    private String secret;

    public String generateToken(String username){
        Date expiriesDate = Date.from(ZonedDateTime.now().plusMinutes(5).toInstant());
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username",username)
                .withIssuedAt(new Date())
                .withExpiresAt(expiriesDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String verifyToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("username").asString();
    }
}
