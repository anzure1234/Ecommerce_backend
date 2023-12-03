package com.example.ecommerce_backend.Repositories;

import com.example.ecommerce_backend.Models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long>{
    List<ProductImage> findByProductId(Long productId);
}
