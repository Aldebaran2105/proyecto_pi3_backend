package com.example.proyecto_pi3_backend.Orders.domain;

import com.example.proyecto_pi3_backend.Feedback.domain.Feedback;
import com.example.proyecto_pi3_backend.OrderDetails.domain.OrderDetails;
import com.example.proyecto_pi3_backend.User.domain.Users;
import com.example.proyecto_pi3_backend.Vendors.domain.Vendors;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp pickup_time;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedback = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendors vendor;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails = new ArrayList<>();
}
