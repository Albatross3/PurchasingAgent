package com.demo.agent.controller.api;

import com.demo.agent.controller.dto.OrderCreateRequest;
import com.demo.agent.domain.Order;
import com.demo.agent.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/new")
    public Order createOrders(@RequestBody OrderCreateRequest orderCreateRequest) {
        return orderService.createOrder(
                orderCreateRequest.phoneNumber(),
                orderCreateRequest.address(),
                orderCreateRequest.orderItems()
        );
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }
}
