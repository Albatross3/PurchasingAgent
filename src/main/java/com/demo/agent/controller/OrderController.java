package com.demo.agent.controller;

import com.demo.agent.domain.Order;
import com.demo.agent.domain.OrderItem;
import com.demo.agent.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String viewOrderPage(Model model) {
        model.addAttribute("currentDay", "원하는 날짜 입력");
        return "order";
    }

    @GetMapping("/orders/date")
    public String viewOrderByDatePage(@RequestParam LocalDate localDate, Model model) {
        model.addAttribute("currentDay", localDate);
        List<Order> ordersByDate = orderService.findByDate(localDate.toString());
        model.addAttribute("ordersByDate", ordersByDate);
        return "order";

    }

    @GetMapping("/orderItems")
    public String viewOrderItemsPage(Model model) {
        model.addAttribute("orderId", "주문 아이디 입력");
        return "order-items";
    }

    @GetMapping("/orderItems/{orderId}")
    public String viewOrderItemsByIdPage(@PathVariable("orderId") UUID orderId, Model model) {
        model.addAttribute("orderId", orderId);
        List<OrderItem> itemsById = orderService.findItemsById(orderId);
        model.addAttribute("items", itemsById);
        return "order-items";
    }
}
