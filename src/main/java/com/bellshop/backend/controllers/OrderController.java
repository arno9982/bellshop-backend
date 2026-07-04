package com.bellshop.backend.controllers;

import com.bellshop.backend.models.Order;
import com.bellshop.backend.models.OrderStatus;
import com.bellshop.backend.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // --- ACCÈS PUBLIC (Client) ---

    // Soumettre une nouvelle demande (Achat direct ou sur-mesure)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // --- ACCÈS ADMIN ---

    // Voir toutes les commandes reçues (triées par date décroissante)
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Voir une commande précise
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Filtrer les commandes par statut (ex: /api/orders/status/NOUVELLE)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // Changer le statut d'une commande (ex: passer de NOUVELLE à VALIDEE)
    // Utilisation de @RequestParam (ex: PUT /api/orders/1/status?status=VALIDEE)
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}