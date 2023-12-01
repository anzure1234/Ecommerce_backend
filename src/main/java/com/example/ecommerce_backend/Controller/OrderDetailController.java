package com.example.ecommerce_backend.Controller;

import com.example.ecommerce_backend.Dtos.OrderDetailDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetailDto orderDetailDto){
        return ResponseEntity.ok("create order detail"+orderDetailDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("get order detail"+id);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId){
        return ResponseEntity.ok("get order detail with order id"+orderId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                               @Valid @RequestBody OrderDetailDto newOrderDetailDto){
        return ResponseEntity.ok("update order detail"+id+"with data"+newOrderDetailDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("delete order detail"+id);
    }
}
