package com.yousef.bitlyClone.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtils {
    String extractJwtFromHeader(String header);
    String extractJwtFromHeader(HttpServletRequest request);
    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    Boolean isTokenExpired(String token);
    Boolean isTokenValid(String token, UserDetails userDetails);
}
