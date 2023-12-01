package com.example.ecommerce_backend.Repositories;

import com.example.ecommerce_backend.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long UserId);
}
