package com.yousef.bitlyClone.services.auth;

import com.yousef.bitlyClone.dtos.AuthResponse;
import com.yousef.bitlyClone.dtos.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
}
