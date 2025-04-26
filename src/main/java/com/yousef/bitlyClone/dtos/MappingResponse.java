package com.yousef.bitlyClone.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MappingResponse(
        Long id,
        String originalUrl,
        String shortenedUrl,
        int ClickCount,
        LocalDateTime createdAt,
        String username
) {
}
