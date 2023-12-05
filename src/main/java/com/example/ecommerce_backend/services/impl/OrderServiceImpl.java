package com.example.ecommerce_backend.services.impl;

import com.example.ecommerce_backend.dtos.OrderDto;
import com.example.ecommerce_backend.enums.Status;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.Order;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repositories.OrderRepository;
import com.example.ecommerce_backend.repositories.UserRepository;
import com.example.ecommerce_backend.responses.OrderResponse;
import com.example.ecommerce_backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public Order createOrder(OrderDto orderDto) throws DataNotFoundException {
        User user =userRepository.findById(orderDto.getUserId()).orElseThrow(()->new DataNotFoundException("User not found: "+orderDto.getUserId()));

        //Dùng thư viện ModelMapper thay vì builder
        modelMapper.typeMap(OrderDto.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDto, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(Status.PENDING);

        //check shipping date > order date
        LocalDate shippingDate = orderDto.getShippingDate() ==null? LocalDate.now() : orderDto.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Shipping date must be at least today");
        }
        order.setActive(true);
        order.setShippingDate(shippingDate);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) throws DataNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Order not found: "+id));
    }


    @Override
    public List<Order> getAllOrdersByUser(Long id) {
        return orderRepository.findByUserId(id);

    }

    @Override
    public Order updateOrderById(Long id, OrderDto orderDto) throws DataNotFoundException {
         Order order = orderRepository.findById(id).orElseThrow(()->new DataNotFoundException("Order not found: "+id));
         User user = userRepository.findById(orderDto.getUserId()).orElseThrow(()->new DataNotFoundException("User not found: "+orderDto.getUserId()));
        modelMapper.typeMap(OrderDto.class,Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDto,order);

        if(!orderDto.getStatus().equals(Status.DELIVERRED)){
            order.setStatus(orderDto.getStatus());
        }
        order.setUser(user);
        return orderRepository.save(order);

    }

    @Override
    public void deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            order.get().setActive(false);
            orderRepository.save(order.get());
        }
    }
}
