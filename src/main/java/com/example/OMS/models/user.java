package com.example.OMS.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
@Getter @Setter
public class user {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "You should enter a username")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "You should enter a password")
    private String password; // store bcrypt-hashed

    @Column(nullable = false)
    private String role; // ROLE_CUSTOMER or ROLE_ADMIN

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private admin admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public com.example.OMS.models.customer getCustomer() {
        return customer;
    }

    public void setCustomer(com.example.OMS.models.customer customer) {
        this.customer = customer;
    }

    public com.example.OMS.models.admin getAdmin() {
        return admin;
    }

    public void setAdmin(com.example.OMS.models.admin admin) {
        this.admin = admin;
    }
}

