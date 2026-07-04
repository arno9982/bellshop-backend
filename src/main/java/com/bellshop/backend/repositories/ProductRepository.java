package com.bellshop.backend.repositories;

import com.bellshop.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Récupérer les produits par catégorie s'ils sont disponibles
    List<Product> findByCategoryAndIsAvailableTrue(String category);
    
    // Récupérer uniquement les produits disponibles pour le catalogue public
    List<Product> findByIsAvailableTrue();
}