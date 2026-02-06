package com.ela.oms.service.Impl;

import com.ela.oms.dto.order.*;
import com.ela.oms.entity.customer.Customer;
import com.ela.oms.entity.order.Order;
import com.ela.oms.entity.order.OrderItem;
import com.ela.oms.entity.product.Product;
import com.ela.oms.exception.ResourceNotFoundException;
import com.ela.oms.repository.CustomerRepository;
import com.ela.oms.repository.OrderRepository;
import com.ela.oms.repository.ProductRepository;
import com.ela.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;


    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(Order.OrderStatus.CREATED);
        double totalAmount = 0.0;

        for (OrderItemRequestDTO itemDTO : requestDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());
            double subtotal = product.getPrice() * itemDTO.getQuantity();
            totalAmount += subtotal;
            order.getItems().add(item);
        }
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return mapToResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatusUpdateRequestDTO requestDTO) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(requestDTO.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return mapToResponseDTO(updatedOrder);
    }


    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order Not Found"));

    }

    private OrderResponseDTO mapToResponseDTO(Order order) {

        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream().map(item -> {
            OrderItemResponseDTO dto = new OrderItemResponseDTO();
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setSubtotal(item.getPrice() * item.getQuantity());
            return dto;
        }).toList();

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderDate(order.getCreatedDate());
        response.setUpdateDate(order.getUpdatedDate());
        response.setItems(itemDTOs);

        return response;
    }

}
