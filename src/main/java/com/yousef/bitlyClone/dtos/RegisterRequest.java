package com.yousef.bitlyClone.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotBlank
        @NotEmpty
        @Email
        String email,
        @NotBlank
        @NotEmpty
        String password,
        @NotBlank
        @NotEmpty
        String firstName,
        @NotBlank
        @NotEmpty
        String lastName,
        String username
) {
}
