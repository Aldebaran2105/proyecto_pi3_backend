package com.example.proyecto_pi3_backend.Vendors.domain;

import com.example.proyecto_pi3_backend.Vendors.dto.VendorResponseDTO;
import com.example.proyecto_pi3_backend.Vendors.infrastructure.VendorsRepository;
import com.example.proyecto_pi3_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorsService {
    private final VendorsRepository vendorsRepository;
    private final ModelMapper modelMapper;

    public List<VendorResponseDTO> getAllVendors() {
        return vendorsRepository.findAll().stream()
                .map(vendor -> modelMapper.map(vendor, VendorResponseDTO.class))
                .collect(Collectors.toList());
    }

    public VendorResponseDTO getVendorById(Long id) {
        Vendors vendor = vendorsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor no encontrado con ID: " + id));
        
        return modelMapper.map(vendor, VendorResponseDTO.class);
    }
}
