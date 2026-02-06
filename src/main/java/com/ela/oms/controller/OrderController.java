package com.ela.oms.controller;

import com.ela.oms.dto.order.OrderRequestDTO;
import com.ela.oms.dto.order.OrderResponseDTO;
import com.ela.oms.dto.order.OrderStatusUpdateRequestDTO;
import com.ela.oms.entity.order.Order;
import com.ela.oms.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderRequestDTO request) {

        OrderResponseDTO response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, requestDTO));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order response = orderService.getOrder(orderId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
