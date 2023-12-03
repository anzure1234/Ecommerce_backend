package com.example.ecommerce_backend.Services.Impl;

import com.example.ecommerce_backend.Dtos.ProductDto;
import com.example.ecommerce_backend.Dtos.ProductImageDto;
import com.example.ecommerce_backend.Models.Category;
import com.example.ecommerce_backend.Models.Product;
import com.example.ecommerce_backend.Models.ProductImage;
import com.example.ecommerce_backend.Repositories.CategoryRepository;
import com.example.ecommerce_backend.Repositories.ProductImageRepository;
import com.example.ecommerce_backend.Repositories.ProductRepository;
import com.example.ecommerce_backend.Services.ProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Builder
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDto productDto)  {
        Category existingCategory=categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()->new RuntimeException("Category not found"));
        Product newProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .category(existingCategory).build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    @Override
    public Page<Product> getAllProduct(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = getProductById(id);
        if(existingProduct!=null){
            Category existingCategory=categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(()->new RuntimeException("Category not found"));
            existingProduct.setName(productDto.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setThumbnail(productDto.getThumbnail());
            existingProduct.setDescription(productDto.getDescription());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(id);
        }
        else throw new RuntimeException("Product not found");
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) {
        Product existingProduct = productRepository.findById(productImageDto.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDto.getImageUrl())
                .build();
        int size=productImageRepository.findByProductId(productId).size();
        if(size>=5){
            throw new RuntimeException("Product's images must be less than 5");
        }
        return productImageRepository.save(newProductImage);
    }
}
