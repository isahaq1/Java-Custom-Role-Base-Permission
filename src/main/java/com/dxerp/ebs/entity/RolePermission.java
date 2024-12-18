package com.dxerp.ebs.entity;
import jakarta.persistence.*;

public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    // Getters and Setters
}
