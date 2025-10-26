package com.example.proyecto_pi3_backend.Vendors.application;

import com.example.proyecto_pi3_backend.Vendors.domain.VendorsService;
import com.example.proyecto_pi3_backend.Vendors.dto.VendorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorsController {
    private final VendorsService vendorsService;

    @GetMapping
    public ResponseEntity<List<VendorResponseDTO>> getAllVendors() {
        return ResponseEntity.ok(vendorsService.getAllVendors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDTO> getVendorById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorsService.getVendorById(id));
    }
}
