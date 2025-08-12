package com.example.OMS.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;

public class jwtAuthenticationFilter extends OncePerRequestFilter {
    private final jwtUtil jwt_Util;
    private final userDetailsServiceImpl userDetailsService;



    public jwtAuthenticationFilter(jwtUtil jwt_Util, userDetailsServiceImpl uds) {
        this.jwt_Util = jwt_Util;
        this.userDetailsService = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwt_Util.validateToken(token)) {
                    Claims claims = jwt_Util.getClaims(token);
                    String username = claims.getSubject();
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } else {
                    sendJsonError(res, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token. Please login again.");
                    return;
                }
            } catch (ExpiredJwtException e) {
                sendJsonError(res, HttpServletResponse.SC_UNAUTHORIZED, "Your token is expired, please login again.");
                return;
            } catch (Exception ex) {
                sendJsonError(res, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return;
            }
       }
        chain.doFilter(req, res);
    }

    private void sendJsonError(HttpServletResponse res, int status, String message) throws IOException {
        res.setStatus(status);
        res.setContentType("application/json");
        res.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}
