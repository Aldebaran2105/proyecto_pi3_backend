package com.example.proyecto_pi3_backend.Feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {
    private Integer rating;
    private String comment;
    private Long userId;
    private Long orderId;
    private Long menuItemId;
}

