package com.example.proyecto_pi3_backend.MenuItems.domain;

import com.example.proyecto_pi3_backend.Availability.domain.Availability;
import com.example.proyecto_pi3_backend.Availability.infrastructure.AvailabilityRepository;
import com.example.proyecto_pi3_backend.MenuItems.dto.MenuItemsRequestDTO;
import com.example.proyecto_pi3_backend.MenuItems.dto.MenuItemsResponseDTO;
import com.example.proyecto_pi3_backend.MenuItems.infrastructure.MenuItemsRepository;
import com.example.proyecto_pi3_backend.Vendors.domain.Vendors;
import com.example.proyecto_pi3_backend.Vendors.infrastructure.VendorsRepository;
import com.example.proyecto_pi3_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemsService {
    private final MenuItemsRepository menuItemsRepository;
    private final VendorsRepository vendorsRepository;
    private final AvailabilityRepository availabilityRepository;

    public List<MenuItemsResponseDTO> getAvailableMenuItemsByVendor(Long vendorId) {
        Vendors vendor = vendorsRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor no encontrado con ID: " + vendorId));
        
        List<MenuItems> menuItems = menuItemsRepository.findAll().stream()
                .filter(item -> item.getVendor().getId().equals(vendorId))
                .collect(Collectors.toList());
        
        Date today = new Date();
        
        return menuItems.stream()
                .map(item -> {
                    // Buscar disponibilidad para hoy
                    List<Availability> availabilities = availabilityRepository.findAll().stream()
                            .filter(avail -> avail.getMenuItem().getId().equals(item.getId()) && 
                                           avail.getDate().equals(today))
                            .collect(Collectors.toList());
                    
                    boolean isAvailable = false;
                    int stock = 0;
                    
                    if (!availabilities.isEmpty()) {
                        Availability todayAvailability = availabilities.get(0);
                        isAvailable = todayAvailability.getIsAvailable();
                        stock = todayAvailability.getStock() != null ? todayAvailability.getStock() : 0;
                    }
                    
                    return new MenuItemsResponseDTO(
                            item.getId(),
                            item.getItemName(),
                            item.getDescription(),
                            item.getPrice(),
                            vendor.getId(),
                            vendor.getName(),
                            isAvailable,
                            stock
                    );
                })
                .collect(Collectors.toList());
    }

    public List<MenuItemsResponseDTO> getAllAvailableMenuItems() {
        List<MenuItems> allMenuItems = menuItemsRepository.findAll();
        Date today = new Date();
        
        return allMenuItems.stream()
                .map(item -> {
                    List<Availability> availabilities = availabilityRepository.findAll().stream()
                            .filter(avail -> avail.getMenuItem().getId().equals(item.getId()) && 
                                           avail.getDate().equals(today))
                            .collect(Collectors.toList());
                    
                    boolean isAvailable = false;
                    int stock = 0;
                    
                    if (!availabilities.isEmpty()) {
                        Availability todayAvailability = availabilities.get(0);
                        isAvailable = todayAvailability.getIsAvailable();
                        stock = todayAvailability.getStock() != null ? todayAvailability.getStock() : 0;
                    }
                    
                    return new MenuItemsResponseDTO(
                            item.getId(),
                            item.getItemName(),
                            item.getDescription(),
                            item.getPrice(),
                            item.getVendor().getId(),
                            item.getVendor().getName(),
                            isAvailable,
                            stock
                    );
                })
                .collect(Collectors.toList());
    }

    public MenuItemsResponseDTO getMenuItemById(Long id) {
        MenuItems item = menuItemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item no encontrado con ID: " + id));
        
        Date today = new Date();
        List<Availability> availabilities = availabilityRepository.findAll().stream()
                .filter(avail -> avail.getMenuItem().getId().equals(item.getId()) && 
                               avail.getDate().equals(today))
                .collect(Collectors.toList());
        
        boolean isAvailable = false;
        int stock = 0;
        
        if (!availabilities.isEmpty()) {
            Availability todayAvailability = availabilities.get(0);
            isAvailable = todayAvailability.getIsAvailable();
            stock = todayAvailability.getStock() != null ? todayAvailability.getStock() : 0;
        }
        
        return new MenuItemsResponseDTO(
                item.getId(),
                item.getItemName(),
                item.getDescription(),
                item.getPrice(),
                item.getVendor().getId(),
                item.getVendor().getName(),
                isAvailable,
                stock
        );
    }
}
