package com.example.proyecto_pi3_backend.User.infrastructure;

import com.example.proyecto_pi3_backend.User.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
