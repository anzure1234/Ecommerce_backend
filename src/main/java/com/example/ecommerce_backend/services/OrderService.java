package com.example.ecommerce_backend.services;

import com.example.ecommerce_backend.dtos.OrderDto;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDto orderDto) throws DataNotFoundException;
    Order getOrderById(Long id) throws DataNotFoundException;
    List<Order> getAllOrdersByUser(Long id);

    Order updateOrderById(Long id , OrderDto orderDto) throws DataNotFoundException;

    void deleteOrder(Long id);

}
