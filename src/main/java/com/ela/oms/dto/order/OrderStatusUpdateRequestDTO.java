package com.ela.oms.dto.order;

import com.ela.oms.entity.order.Order;
import jakarta.validation.constraints.NotNull;

public class OrderStatusUpdateRequestDTO {

    @NotNull
    private Order.OrderStatus status;

    public Order.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(Order.OrderStatus status) {
        this.status = status;
    }
}

