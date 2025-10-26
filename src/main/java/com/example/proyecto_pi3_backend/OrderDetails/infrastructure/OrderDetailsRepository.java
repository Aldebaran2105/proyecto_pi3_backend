package com.example.proyecto_pi3_backend.OrderDetails.infrastructure;

import com.example.proyecto_pi3_backend.OrderDetails.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
