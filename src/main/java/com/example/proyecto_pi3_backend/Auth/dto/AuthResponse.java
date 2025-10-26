package com.example.proyecto_pi3_backend.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
}

