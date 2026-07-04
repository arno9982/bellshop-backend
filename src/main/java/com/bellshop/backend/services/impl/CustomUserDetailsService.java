package com.bellshop.backend.services.impl;

import com.bellshop.backend.models.Admin;
import com.bellshop.backend.repositories.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public CustomUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // On cherche l'admin dans PostgreSQL
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin non trouvé avec le pseudo : " + username));

        // On convertit notre modèle 'Admin' en un objet 'User' compris par Spring Security
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.emptyList() // Pas de rôles complexes nécessaires pour l'instant (Simple Admin)
        );
    }
}