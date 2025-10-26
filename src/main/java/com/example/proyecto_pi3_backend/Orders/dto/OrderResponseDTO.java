package com.example.proyecto_pi3_backend.Orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String status;
    private Timestamp pickup_time;
    private Long userId;
    private String userName;
    private Long vendorId;
    private String vendorName;
    private List<OrderDetailResponseDTO> items;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailResponseDTO {
        private Long id;
        private String itemName;
        private Integer quantity;
        private String price;
    }
}

