package com.demo.agent.controller.dto;

import com.demo.agent.domain.OrderItem;

import java.util.List;

public record OrderCreateRequest(String phoneNumber, String address, List<OrderItem> orderItems) {
}
