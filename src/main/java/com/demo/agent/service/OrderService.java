package com.demo.agent.service;

import com.demo.agent.domain.Order;
import com.demo.agent.domain.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(String phoneNumber, String address, List<OrderItem> orderItems);

    List<Order> findAllOrders();

    List<Order> findByDate(String date);

    List<OrderItem> findItemsById(UUID orderId);

}
