package com.example.OMS.auth;


import com.example.OMS.models.user;
import com.example.OMS.repository.userRepo;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class userDetailsServiceImpl implements UserDetailsService {
    private final userRepo user_Repo;
    public userDetailsServiceImpl(userRepo user_Repo) { this.user_Repo = user_Repo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user u = user_Repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(u.getRole()))
        );
    }
}

