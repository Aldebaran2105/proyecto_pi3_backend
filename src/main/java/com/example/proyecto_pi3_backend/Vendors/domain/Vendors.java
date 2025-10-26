package com.example.proyecto_pi3_backend.Vendors.domain;

import com.example.proyecto_pi3_backend.MenuItems.domain.MenuItems;
import com.example.proyecto_pi3_backend.Orders.domain.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Vendors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String ubication;

    @OneToMany(mappedBy = "vendor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "vendor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItems> menuItems;
}