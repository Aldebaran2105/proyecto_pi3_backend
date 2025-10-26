package com.example.proyecto_pi3_backend.Orders.infrastructure;

import com.example.proyecto_pi3_backend.Orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
