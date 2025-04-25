package com.yousef.bitlyClone.services.auth;

import com.yousef.bitlyClone.dtos.AuthResponse;
import com.yousef.bitlyClone.dtos.RegisterRequest;
import com.yousef.bitlyClone.models.Role;
import com.yousef.bitlyClone.models.User;
import com.yousef.bitlyClone.repositories.UserRepository;
import com.yousef.bitlyClone.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        var userExists = userRepository.existsByEmail(registerRequest.email());

        if (userExists) {
            throw new IllegalStateException("User already exists");
        }

        var user = User.builder()
                .role(Role.USER.getAuthority())
                .email(registerRequest.email())
                .password(registerRequest.password())
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .createdAt(LocalDateTime.now())
                .build();

        var savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .token(jwtUtils.generateToken(savedUser))
                .build();
    }
}
