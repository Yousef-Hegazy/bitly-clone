package com.yousef.bitlyClone.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtUtilsImpl implements JwtUtils {

    @Value("${jwt-config.secret}")
    private String secret;

    @Override
    public String extractJwtFromHeader(String header) {
        return header.replace("Bearer ", "");
    }

    @Override
    public String extractJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer")) return null;

        return extractJwtFromHeader(authHeader);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities()
                .stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.joining(","));


        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(getKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .decryptWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        var username = userDetails.getUsername();
        var subj = extractUsername(token);

        return !isTokenExpired(token) && username.equals(subj) && isTokenValid(token);
    }

    private Boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
