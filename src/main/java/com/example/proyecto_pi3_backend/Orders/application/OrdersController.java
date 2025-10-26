package com.example.proyecto_pi3_backend.Orders.application;

import com.example.proyecto_pi3_backend.Orders.domain.OrdersService;
import com.example.proyecto_pi3_backend.Orders.dto.OrderRequestDTO;
import com.example.proyecto_pi3_backend.Orders.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok(ordersService.createOrder(orderRequestDTO));
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.payOrder(orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.getOrderById(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ordersService.getOrdersByUserId(userId));
    }

    @PostMapping("/{orderId}/ready")
    public ResponseEntity<OrderResponseDTO> markOrderAsReady(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.markOrderAsReady(orderId));
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<OrderResponseDTO> markOrderAsCompleted(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.markOrderAsCompleted(orderId));
    }
}
