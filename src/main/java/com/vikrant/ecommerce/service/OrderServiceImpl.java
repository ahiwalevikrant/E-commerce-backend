package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.*;
import com.vikrant.ecommerce.exception.CustomerNotFound;
import com.vikrant.ecommerce.repository.CustomerRepo;
import com.vikrant.ecommerce.repository.OrderRepo;
import com.vikrant.ecommerce.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    
    @Autowired
    private SessionRepo sessionRepo;
    
    @Autowired
    private CustomerRepo customerRepo;

    private Customer getCustomerFromToken(String token) throws CustomerNotFound {
        UserSession session = sessionRepo.findByToken(token)
            .orElseThrow(() -> new CustomerNotFound("Invalid session token"));
        return customerRepo.findById(session.getUserId())
            .orElseThrow(() -> new CustomerNotFound("Customer not found"));
    }

    @Override
    public Order createOrder(Order order, String token) {
        Customer customer = getCustomerFromToken(token);
        
        // Set order date if not set
        if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }
        
        // Set default order status if not set
        if (order.getOrderStatus() == null) {
            order.setOrderStatus(OrderStatusValues.PENDING);
        }
        
        // Calculate total from cart items
        Cart cart = customer.getCustomercart();
        if (cart != null && cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            double total = cart.getCartItems().stream()
                .mapToDouble(item -> item.getCartProduct().getPrice() * item.getCartItemQuantity())
                .sum();
            order.setTotal(total);
            order.setOrdercartitems(cart.getCartItems());
        }
        
        order.setCustomer(customer);
        
        // If address not set, try to get from customer
        if (order.getAddress() == null && customer.getAddress() != null && !customer.getAddress().isEmpty()) {
            order.setAddress(customer.getAddress().get("shipping"));
        }
        
        return orderRepo.save(order);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order updateOrderStatus(Integer orderId, OrderStatusValues status) {
        Order order = getOrderById(orderId);
        order.setOrderStatus(status);
        return orderRepo.save(order);
    }

    @Override
    public String cancelOrder(Integer orderId, String token) {
        Customer customer = getCustomerFromToken(token);
        Order order = getOrderById(orderId);
        
        // Verify the order belongs to the customer
        if (!order.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            throw new RuntimeException("Order does not belong to this customer");
        }
        
        order.setOrderStatus(OrderStatusValues.CANCELLED);
        orderRepo.save(order);
        
        return "Order cancelled successfully";
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        return orderRepo.findByDate(date);
    }
}
