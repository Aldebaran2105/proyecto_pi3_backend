package com.example.proyecto_pi3_backend.OrderDetails.application;

import com.example.proyecto_pi3_backend.OrderDetails.domain.OrderDetailsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailsController {
    public OrderDetailsService orderDetailsService;
}
