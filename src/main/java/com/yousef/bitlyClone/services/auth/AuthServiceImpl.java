package com.yousef.bitlyClone.services.auth;

import com.yousef.bitlyClone.dtos.AuthResponse;
import com.yousef.bitlyClone.dtos.LoginRequest;
import com.yousef.bitlyClone.dtos.RegisterRequest;
import com.yousef.bitlyClone.models.Role;
import com.yousef.bitlyClone.models.User;
import com.yousef.bitlyClone.repositories.UserRepository;
import com.yousef.bitlyClone.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        var userExists = userRepository.existsByEmail(registerRequest.email());

        if (userExists) {
            throw new IllegalStateException("User already exists");
        }

        var user = User.builder()
                .role(Role.USER.getAuthority())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .username(registerRequest.username())
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .createdAt(LocalDateTime.now())
                .build();

        var savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getFullName())
                .token(jwtUtils.generateToken(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        if (!auth.isAuthenticated()) {
            throw new IllegalStateException("Invalid credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(auth);

        var user = (User) auth.getPrincipal();

        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .token(jwtUtils.generateToken(user))
                .build();
    }
}
