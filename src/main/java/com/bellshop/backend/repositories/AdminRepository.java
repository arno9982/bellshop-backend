package com.bellshop.backend.repositories;

import com.bellshop.backend.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    // Utile pour la future sécurité (recherche de l'admin par son username)
    Optional<Admin> findByUsername(String username);
}
