package com.example.proyecto_pi3_backend.Availability.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponseDTO {
    private Long id;
    private Date date;
    private Integer stock;
    private Boolean isAvailable;
    private String itemName;
    private Long menuItemId;
}

