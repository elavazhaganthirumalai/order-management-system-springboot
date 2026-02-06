package com.ela.oms.service;

import com.ela.oms.dto.order.OrderRequestDTO;
import com.ela.oms.dto.order.OrderResponseDTO;
import com.ela.oms.dto.order.OrderStatusUpdateRequestDTO;
import com.ela.oms.entity.order.Order;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO requestDTO);

    Order getOrder(Long orderId);

    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatusUpdateRequestDTO requestDTO);
}

