package com.yousef.bitlyClone.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty
        @NotBlank
        @Email
        String email,
        @NotEmpty
        @NotBlank
        String password
) {
}
