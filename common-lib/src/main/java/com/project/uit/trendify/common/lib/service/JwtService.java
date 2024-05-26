package com.project.uit.trendify.common.lib.service;

import com.project.uit.trendify.common.lib.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "0ffd01646fe0d6232f462781a87b9314aec2811a716aadfc17dee3045423490b";
    public String extractUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getPublicSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public Long extractUserId(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getPublicSigningKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    private SecretKey getPublicSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken (
            UserDetails userDetails
    ) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", ((User) userDetails).getId());
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date((System.currentTimeMillis())))
                .signWith(getPublicSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        System.out.print("EMAIL -- " + userEmail + " -- TOKEN ");
        System.out.println(userEmail.equals(userDetails.getUsername()) ? "VALID" : "NOT VALID");
        return userEmail.equals(userDetails.getUsername());
    }
}
