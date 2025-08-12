package com.example.OMS.auth.utils;

import com.example.OMS.auth.jwtUtil;
import com.example.OMS.repository.customerRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class securityUtils {

    private final customerRepo customerRepository; // repo for customers
    private final jwtUtil JWT_Util;

    public securityUtils(customerRepo customerRepository, jwtUtil JWT_Util) {
        this.customerRepository = customerRepository;
        this.JWT_Util = JWT_Util;
    }

    public Long getUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return JWT_Util.getClaims(token).get("userId", Long.class);
        }
        return null;
    }

    public void validateCustomerAccess(HttpServletRequest request, Long requestedCustomerId) {
        Long userIdFromToken = getUserIdFromRequest(request);
        if (userIdFromToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid token.");
        }

        // find the customerId that belongs to this userId
        Long customerIdFromDb = customerRepository.findByUsers_Id(userIdFromToken)
                .map(c -> c.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found for this user."
                ));

        if (!customerIdFromDb.equals(requestedCustomerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this resource.");
        }
    }

    public boolean hasRole(String role) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}
