package com.example.proyecto_pi3_backend.Auth.domain;

import com.example.proyecto_pi3_backend.Auth.dto.AuthResponse;
import com.example.proyecto_pi3_backend.Auth.dto.LoginRequest;
import com.example.proyecto_pi3_backend.Auth.dto.RegisterRequest;
import com.example.proyecto_pi3_backend.User.domain.Role;
import com.example.proyecto_pi3_backend.User.domain.Users;
import com.example.proyecto_pi3_backend.User.infrastructure.UserRepository;
import com.example.proyecto_pi3_backend.config.JwtService;
import com.example.proyecto_pi3_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(request.getEmail()))) {
            throw new RuntimeException("El email ya está registrado");
        }

        Users user = new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() != null) {
            try {
                user.setRole(Role.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setRole(Role.USER);
            }
        } else {
            user.setRole(Role.USER);
        }

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                token
        );
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(userDetails.getUsername()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole() != null ? user.getRole().name() : "USER",
                token
        );
    }
}
