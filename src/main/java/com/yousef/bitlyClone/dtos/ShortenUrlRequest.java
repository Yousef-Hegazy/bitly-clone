package com.yousef.bitlyClone.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ShortenUrlRequest(
        @NotEmpty
        @NotBlank
        String originalUrl
) {
}
