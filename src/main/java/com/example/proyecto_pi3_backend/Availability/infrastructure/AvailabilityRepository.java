package com.example.proyecto_pi3_backend.Availability.infrastructure;

import com.example.proyecto_pi3_backend.Availability.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
