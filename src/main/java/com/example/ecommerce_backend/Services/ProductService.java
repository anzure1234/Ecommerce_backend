package com.example.ecommerce_backend.Services;

import com.example.ecommerce_backend.Dtos.ProductDto;
import com.example.ecommerce_backend.Dtos.ProductImageDto;
import com.example.ecommerce_backend.Models.Product;
import com.example.ecommerce_backend.Models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product createProduct(ProductDto productDTO) throws Exception;
    Product getProductById(long id) throws Exception;
    Page<Product> getAllProducts(PageRequest pageRequest);
    Product updateProduct(long id, ProductDto productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDto productImageDTO) throws Exception;
}
