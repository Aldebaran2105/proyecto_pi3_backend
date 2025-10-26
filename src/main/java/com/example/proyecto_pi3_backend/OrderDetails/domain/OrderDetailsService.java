package com.example.proyecto_pi3_backend.OrderDetails.domain;

import com.example.proyecto_pi3_backend.OrderDetails.infrastructure.OrderDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {
    public OrderDetailsRepository orderDetailsRepository;
}
