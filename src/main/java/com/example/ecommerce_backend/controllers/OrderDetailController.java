package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.dtos.OrderDetailDto;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.OrderDetail;
import com.example.ecommerce_backend.responses.OrderDetailResponse;
import com.example.ecommerce_backend.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetailDto orderDetailDto){
        try {
            OrderDetail orderdetail=orderDetailService.createOrderDetail(orderDetailDto);
            return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderdetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws DataNotFoundException {
        OrderDetail orderDetail=orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@Valid @PathVariable("orderId") Long orderId){
        List<OrderDetail> orderDetailList = orderDetailService.getAllOrderDetail(orderId);
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetailList(orderDetailList));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                               @Valid @RequestBody OrderDetailDto orderDetailDto){
        OrderDetail orderDetail= orderDetailService.updateOrderDetail(id,orderDetailDto);
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("delete order with "+id);
        } catch (DataNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
