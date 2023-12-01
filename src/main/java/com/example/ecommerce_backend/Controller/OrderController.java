package com.example.ecommerce_backend.Controller;

import com.example.ecommerce_backend.Dtos.OrderDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
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
            return ResponseEntity.ok("create order"+orderDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable("user_id") Long userId){
        try{
            return ResponseEntity.ok("get order by user id"+userId);
    }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody OrderDto orderDto){
        try{
            return ResponseEntity.ok("update order"+id+"with data"+orderDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long id){
        //is_active = false
        try{
            return ResponseEntity.ok("delete order"+id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
