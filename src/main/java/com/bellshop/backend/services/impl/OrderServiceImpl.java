package com.bellshop.backend.services.impl;

import com.bellshop.backend.models.Order;
import com.bellshop.backend.models.OrderStatus;
import com.bellshop.backend.repositories.OrderRepository;
import com.bellshop.backend.services.OrderService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        // Logique métier : Sécurité et validation de base
        if (order.getClientPhone() == null || order.getClientPhone().trim().isEmpty()) {
            throw new RuntimeException("Le numéro WhatsApp est obligatoire pour valider la commande.");
        }
        
        // On force le statut initial à NOUVELLE, peu importe ce que le frontend envoie
        order.setStatus(OrderStatus.NOUVELLE);
        
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'id : " + id));
    }

    @Override
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}