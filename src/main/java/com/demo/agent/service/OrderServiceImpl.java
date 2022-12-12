package com.demo.agent.service;

import com.demo.agent.domain.Order;
import com.demo.agent.domain.OrderItem;
import com.demo.agent.domain.OrderStatus;
import com.demo.agent.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String phoneNumber, String address, List<OrderItem> orderItems) {
        LocalDateTime createdTime = LocalDateTime.now();
        Order order = new Order(UUID.randomUUID(), phoneNumber, address,
                orderItems, OrderStatus.ACCEPTED, createdTime, createdTime);
        orderRepository.insert(order);
        return order;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByDate(String date) {
        return orderRepository.findByDate(date);
    }

    @Override
    public List<OrderItem> findItemsById(UUID orderId) {
        return orderRepository.findById(orderId);
    }
}
