package com.bellshop.backend.repositories;


import com.bellshop.backend.models.Order;
import com.bellshop.backend.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Pour filtrer les commandes par statut (ex: NOUVELLE, VALIDEE)
    List<Order> findByStatus(OrderStatus status);
    
    // Pour afficher les demandes de la plus récente à la plus ancienne
    List<Order> findAllByOrderByCreatedAtDesc();
}
