package com.yousef.bitlyClone.dtos;

import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String email,
        String token
) {
}
