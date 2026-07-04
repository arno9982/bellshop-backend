package com.bellshop.backend.services;

import com.bellshop.backend.models.Order;
import com.bellshop.backend.models.OrderStatus;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getOrdersByStatus(OrderStatus status);
    Order getOrderById(Long id);
    Order updateOrderStatus(Long id, OrderStatus newStatus);
}