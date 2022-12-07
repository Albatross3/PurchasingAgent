package com.demo.agent.controller.api;

import com.demo.agent.domain.Order;
import com.demo.agent.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/v1/orders")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }
}
