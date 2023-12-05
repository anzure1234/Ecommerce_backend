package com.example.ecommerce_backend.services.impl;

import com.example.ecommerce_backend.dtos.OrderDetailDto;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.Order;
import com.example.ecommerce_backend.models.OrderDetail;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.repositories.OrderDetailRepository;
import com.example.ecommerce_backend.repositories.OrderRepository;
import com.example.ecommerce_backend.repositories.ProductRepository;
import com.example.ecommerce_backend.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws DataNotFoundException {
        Order order = orderRepository.findById(orderDetailDto.getOrderId()).orElseThrow(()-> new DataNotFoundException("Order id not found"));
        Product product = productRepository.findById(orderDetailDto.getProductId()).orElseThrow(()->new DataNotFoundException("Product id not found"));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDto.getNumberOfProducts())
                .totalMoney(orderDetailDto.getTotalMoney())
                .color(orderDetailDto.getColor())
                .price(orderDetailDto.getPrice())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(()-> new DataNotFoundException("OrderDetail id not found"));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(()-> new RuntimeException("OrderDetail not found"));
        Order order = orderRepository.findById(orderDetailDto.getOrderId()).orElseThrow(()-> new RuntimeException("Order not found"));
        Product product = productRepository.findById(orderDetailDto.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setColor(orderDetailDto.getColor());
        orderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        orderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProducts());
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);

    }

    @Override
    public void deleteOrderDetail(Long id) throws DataNotFoundException {
        orderDetailRepository.findById(id).orElseThrow(()-> new DataNotFoundException("OrderDetail not found"));
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getAllOrderDetail(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

}
