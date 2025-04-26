package com.yousef.bitlyClone.services.auth;

import com.yousef.bitlyClone.dtos.AuthResponse;
import com.yousef.bitlyClone.dtos.LoginRequest;
import com.yousef.bitlyClone.dtos.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse login(@Valid LoginRequest loginRequest);
}
