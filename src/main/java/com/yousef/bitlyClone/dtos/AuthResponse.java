package com.yousef.bitlyClone.dtos;

public record AuthResponse(
        String id,
        String email,
        String token
) {
}
