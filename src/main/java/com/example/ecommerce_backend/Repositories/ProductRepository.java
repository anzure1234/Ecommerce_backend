package com.example.ecommerce_backend.Repositories;

import com.example.ecommerce_backend.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String title);

    Page<Product> findAll(Pageable pageable);
}
