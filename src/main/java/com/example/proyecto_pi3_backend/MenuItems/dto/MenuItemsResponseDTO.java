package com.example.proyecto_pi3_backend.MenuItems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemsResponseDTO {
    private Long id;
    private String itemName;
    private String description;
    private String price;
    private Long vendorId;
    private String vendorName;
    private Boolean isAvailable;
    private Integer stock;
}
