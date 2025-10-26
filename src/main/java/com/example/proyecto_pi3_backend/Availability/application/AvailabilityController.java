package com.example.proyecto_pi3_backend.Availability.application;

import com.example.proyecto_pi3_backend.Availability.domain.AvailabilityService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailabilityController {
    public AvailabilityService availabilityService;
}
