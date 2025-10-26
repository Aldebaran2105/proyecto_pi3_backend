package com.example.proyecto_pi3_backend.Orders.domain;

import com.example.proyecto_pi3_backend.MenuItems.domain.MenuItems;
import com.example.proyecto_pi3_backend.MenuItems.infrastructure.MenuItemsRepository;
import com.example.proyecto_pi3_backend.OrderDetails.domain.OrderDetails;
import com.example.proyecto_pi3_backend.Orders.dto.OrderRequestDTO;
import com.example.proyecto_pi3_backend.Orders.dto.OrderResponseDTO;
import com.example.proyecto_pi3_backend.Orders.infrastructure.OrdersRepository;
import com.example.proyecto_pi3_backend.OrderDetails.infrastructure.OrderDetailsRepository;
import com.example.proyecto_pi3_backend.User.domain.Users;
import com.example.proyecto_pi3_backend.User.infrastructure.UserRepository;
import com.example.proyecto_pi3_backend.Vendors.domain.Vendors;
import com.example.proyecto_pi3_backend.Vendors.infrastructure.VendorsRepository;
import com.example.proyecto_pi3_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final VendorsRepository vendorsRepository;
    private final MenuItemsRepository menuItemsRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // Validar usuario
        Users user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + orderRequestDTO.getUserId()));
        
        // Validar vendor
        Vendors vendor = vendorsRepository.findById(orderRequestDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor no encontrado con ID: " + orderRequestDTO.getVendorId()));
        
        // Crear orden
        Orders order = new Orders();
        order.setUser(user);
        order.setVendor(vendor);
        order.setStatus("PENDIENTE_PAGO");
        order.setPickup_time(new Timestamp(System.currentTimeMillis()));
        
        order = ordersRepository.save(order);
        
        // Crear order details
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderRequestDTO.OrderItemDTO itemDTO : orderRequestDTO.getItems()) {
            MenuItems menuItem = menuItemsRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item no encontrado con ID: " + itemDTO.getMenuItemId()));
            
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrder(order);
            orderDetail.setMenuItem(menuItem);
            orderDetail.setQuantity(itemDTO.getQuantity());
            
            orderDetailsList.add(orderDetailsRepository.save(orderDetail));
        }
        
        // Convertir a DTO
        return convertToOrderResponseDTO(order, orderDetailsList);
    }

    @Transactional
    public OrderResponseDTO payOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + orderId));
        
        if ("PENDIENTE_PAGO".equals(order.getStatus())) {
            order.setStatus("PAGADO");
            order = ordersRepository.save(order);
        } else {
            throw new RuntimeException("La orden ya ha sido procesada");
        }
        
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll().stream()
                .filter(od -> od.getOrder().getId().equals(orderId))
                .collect(Collectors.toList());
        
        return convertToOrderResponseDTO(order, orderDetails);
    }

    public OrderResponseDTO getOrderById(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + orderId));
        
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll().stream()
                .filter(od -> od.getOrder().getId().equals(orderId))
                .collect(Collectors.toList());
        
        return convertToOrderResponseDTO(order, orderDetails);
    }

    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));
        
        List<Orders> orders = ordersRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .collect(Collectors.toList());
        
        return orders.stream()
                .map(order -> {
                    List<OrderDetails> orderDetails = orderDetailsRepository.findAll().stream()
                            .filter(od -> od.getOrder().getId().equals(order.getId()))
                            .collect(Collectors.toList());
                    return convertToOrderResponseDTO(order, orderDetails);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO markOrderAsReady(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + orderId));
        
        if ("PAGADO".equals(order.getStatus())) {
            order.setStatus("LISTO_PARA_RECOJO");
            order = ordersRepository.save(order);
        } else {
            throw new RuntimeException("La orden no está en estado PAGADO");
        }
        
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll().stream()
                .filter(od -> od.getOrder().getId().equals(orderId))
                .collect(Collectors.toList());
        
        return convertToOrderResponseDTO(order, orderDetails);
    }

    @Transactional
    public OrderResponseDTO markOrderAsCompleted(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + orderId));
        
        if ("LISTO_PARA_RECOJO".equals(order.getStatus())) {
            order.setStatus("COMPLETADO");
            order = ordersRepository.save(order);
        } else {
            throw new RuntimeException("La orden no está lista para recoger");
        }
        
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll().stream()
                .filter(od -> od.getOrder().getId().equals(orderId))
                .collect(Collectors.toList());
        
        return convertToOrderResponseDTO(order, orderDetails);
    }

    private OrderResponseDTO convertToOrderResponseDTO(Orders order, List<OrderDetails> orderDetails) {
        List<OrderResponseDTO.OrderDetailResponseDTO> items = orderDetails.stream()
                .map(od -> new OrderResponseDTO.OrderDetailResponseDTO(
                        od.getId(),
                        od.getMenuItem().getItemName(),
                        od.getQuantity(),
                        od.getMenuItem().getPrice()
                ))
                .collect(Collectors.toList());
        
        return new OrderResponseDTO(
                order.getId(),
                order.getStatus(),
                order.getPickup_time(),
                order.getUser().getId(),
                order.getUser().getFirstName() + " " + order.getUser().getLastName(),
                order.getVendor().getId(),
                order.getVendor().getName(),
                items
        );
    }
}
