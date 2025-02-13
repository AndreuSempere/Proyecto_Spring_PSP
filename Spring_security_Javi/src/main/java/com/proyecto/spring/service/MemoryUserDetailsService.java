package com.proyecto.spring.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryUserDetailsService implements UserDetailsService {

    private static final List<UserDetails> USERS = new ArrayList<>();

    static {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        USERS.add(User.withUsername("user1")
                      .password(passwordEncoder.encode("password1"))
                      .roles("USER")
                      .build());
        USERS.add(User.withUsername("admin")
                      .password(passwordEncoder.encode("admin"))
                      .roles("ADMIN")
                      .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return USERS.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
