package com.example.ecommerce_backend.services;

import com.example.ecommerce_backend.dtos.OrderDetailDto;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws DataNotFoundException;

    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto);

    void deleteOrderDetail(Long id) throws DataNotFoundException;

    List<OrderDetail> getAllOrderDetail(Long orderId);

}
