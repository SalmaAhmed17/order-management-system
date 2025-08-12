package com.example.OMS.service;


import com.example.OMS.DTOs.*;
import com.example.OMS.models.*;
import com.example.OMS.repository.*;
import com.example.OMS.auth.jwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class authService {

    private final userRepo user_Repo;
    private final customerRepo customer_Repo;
    private final adminRepo admin_Repo;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtil jwtUtil;

    public authService(userRepo user_Repo, customerRepo customer_Repo, adminRepo admin_Repo,
                       PasswordEncoder passwordEncoder, jwtUtil jwtUtil) {
        this.user_Repo = user_Repo;
        this.customer_Repo = customer_Repo;
        this.admin_Repo = admin_Repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> registerCustomer(customerRegistrationRequest req) {
        if (user_Repo.findByUsername(req.getUsername()).isPresent()) {
            return Map.of("error", "Username already exists!");
        }
        if (customer_Repo.findByEmail(req.getEmail()).isPresent()) {
            return Map.of("error", "Email already used before, and it should be unique");
        }

        customer c = new customer();
        c.setName(req.getName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        customer_Repo.save(c);

        user u = new user();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole("ROLE_CUSTOMER");
        u.setCustomer(c);
        user_Repo.save(u);

        return Map.of("message", "Customer registered");
    }

    public Map<String, String> registerAdmin(adminRegistrationRequest req) {
        if (user_Repo.findByUsername(req.getUsername()).isPresent()) {
            return Map.of("error", "Username already exists!");
        }

        admin a = new admin();
        a.setName(req.getName());
        admin_Repo.save(a);

        user u = new user();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole("ROLE_ADMIN");
        u.setAdmin(a);
        user_Repo.save(u);

        return Map.of("message", "Admin registered");
    }

    public Optional<Map<String, Object>> login(loginRequest req) {
        return user_Repo.findByUsername(req.getUsername()).map(user -> {
            if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
                long expiresInMillis = jwtUtil.getClaims(token).getExpiration().getTime() - System.currentTimeMillis();
                long expiresInMinutes = expiresInMillis / 1000 / 60;
                return Map.<String, Object>of(
                        "token", token,
                        "expiresInMinutes", expiresInMinutes
                );
            } else {
                return null;
            }
        });
    }
}

