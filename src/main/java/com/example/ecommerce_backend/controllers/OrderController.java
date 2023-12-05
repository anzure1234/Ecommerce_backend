package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.dtos.OrderDto;
import com.example.ecommerce_backend.models.Order;
import com.example.ecommerce_backend.responses.OrderResponse;
import com.example.ecommerce_backend.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto orderDto,
                                         BindingResult result){
        try{
            if (result.hasErrors()){
                List<String> errorMessages= result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order= orderService.createOrder(orderDto);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{order_id}")
    public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable("order_id") Long id){
        try{
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
    }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getAllOrderByUserId(@Valid @PathVariable("user_id") Long userId){
        try{
            List<Order> orders = orderService.getAllOrdersByUser(userId);
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{order_id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable("order_id") Long orderId,
            @Valid @RequestBody OrderDto orderDto){
        try{
            Order order = orderService.updateOrderById(orderId,orderDto);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.ok("delete order with "+id+" successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
