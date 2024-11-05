package com.enigmacamp.loanapp.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.loanapp.model.authorize.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${app.tokonyadiashop.jwt.jwt-secret}")
    private String jwtSecret;
    @Value("${app.tokonyadiashop.jwt.app-name}")
    private String appName;
    @Value("${app.tokonyadiashop.jwt.expired}")
    private long jwtExpired;

    private Algorithm algorithm;

    @PostConstruct
    private void initAlgorithm(){
        this.algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(AppUser appUser) {
        return JWT.create()
                .withIssuer(appName)
                .withSubject(appUser.getId())
                .withExpiresAt(Instant.now().plusSeconds(jwtExpired))
                .withIssuedAt(Instant.now())
                .withClaim("roles", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public boolean verifyJwtToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getIssuer().equals(appName);
    }

    public Map<String, String> getUserInfoByToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", decodedJWT.getSubject());
        userInfo.put("role", decodedJWT.getClaim("role").asString());
        return userInfo;

    }
}
