package com.example.proyecto_pi3_backend.MenuItems.dto;

import lombok.Data;

@Data
public class MenuItemsRequestDTO {
    public String itemName;
    public String description;
    public String price;
}
