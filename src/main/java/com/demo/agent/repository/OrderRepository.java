package com.demo.agent.repository;

import com.demo.agent.domain.Order;
import com.demo.agent.domain.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    Order insert(Order order);

    List<Order> findAll();

    List<Order> findByDate(String date);

    List<OrderItem> findById(UUID orderID);
}
