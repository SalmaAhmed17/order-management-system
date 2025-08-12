package com.example.OMS.controller;

import com.example.OMS.DTOs.*;
import com.example.OMS.service.authService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class authController {

    private final authService auth_Service;

    public authController(authService auth_Service) {
        this.auth_Service = auth_Service;
    }

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid customerRegistrationRequest req) {
        Map<String, String> result = auth_Service.registerCustomer(req);
        if (result.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid adminRegistrationRequest req) {
        Map<String, String> result = auth_Service.registerAdmin(req);
        if (result.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequest req) {
        return auth_Service.login(req)
                .map(data -> ResponseEntity.ok(data))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials")));
    }
}
