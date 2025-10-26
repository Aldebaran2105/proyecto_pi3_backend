package com.example.proyecto_pi3_backend.Vendors.infrastructure;

import com.example.proyecto_pi3_backend.Vendors.domain.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorsRepository extends JpaRepository<Vendors, Long> {
}
