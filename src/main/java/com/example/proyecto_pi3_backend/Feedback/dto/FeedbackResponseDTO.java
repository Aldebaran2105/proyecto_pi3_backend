package com.example.proyecto_pi3_backend.Feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private String userName;
    private String itemName;
}

