package com.example.proyecto_pi3_backend.Vendors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponseDTO {
    private Long id;
    private String name;
    private String ubication;
}

