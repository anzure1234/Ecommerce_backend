package com.example.ecommerce_backend.Services;

import com.example.ecommerce_backend.Dtos.ProductDto;
import com.example.ecommerce_backend.Dtos.ProductImageDto;
import com.example.ecommerce_backend.Models.Product;
import com.example.ecommerce_backend.Models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product createProduct (ProductDto productDto);

    Product getProductById(Long id);

    Page<Product> getAllProduct(PageRequest pageRequest);

    Product updateProduct(Long id,ProductDto productDto);

    void deleteProduct(Long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDto productImageDto);
}
