package com.example.proyecto_pi3_backend.MenuItems.application;

import com.example.proyecto_pi3_backend.MenuItems.domain.MenuItemsService;
import com.example.proyecto_pi3_backend.MenuItems.dto.MenuItemsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemsController {
    private final MenuItemsService menuItemsService;

    @GetMapping
    public ResponseEntity<List<MenuItemsResponseDTO>> getAllAvailableMenuItems() {
        return ResponseEntity.ok(menuItemsService.getAllAvailableMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemsResponseDTO> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemsService.getMenuItemById(id));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<MenuItemsResponseDTO>> getAvailableMenuItemsByVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(menuItemsService.getAvailableMenuItemsByVendor(vendorId));
    }
}
