package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.Order;
import com.vikrant.ecommerce.entity.OrderStatusValues;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public Order createOrder(Order order, String token);
    public Order getOrderById(Integer orderId);
    public List<Order> getAllOrders();
    public Order updateOrderStatus(Integer orderId, OrderStatusValues status);
    public String cancelOrder(Integer orderId, String token);
    public List<Order> getOrdersByDate(LocalDate date);
}
