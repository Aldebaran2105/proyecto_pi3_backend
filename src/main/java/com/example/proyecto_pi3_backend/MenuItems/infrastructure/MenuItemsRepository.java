package com.example.proyecto_pi3_backend.MenuItems.infrastructure;

import com.example.proyecto_pi3_backend.MenuItems.domain.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItems, Long> {
}
