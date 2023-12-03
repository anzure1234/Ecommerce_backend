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
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.exceptions.InvalidParamException;
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
    public Product createProduct(ProductDto productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: "+productDTO.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId).
                orElseThrow(()-> new DataNotFoundException(
                        "Cannot find product with id ="+productId));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang(page) và giới hạn(limit)
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(
            long id,
            ProductDto productDTO
    )
            throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            //copy các thuộc tính từ DTO -> Product
            //Có thể sử dụng ModelMapper
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: "+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;

    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDto productImageDTO) throws Exception {
        Product existingProduct = productRepository
                .findById(productImageDTO.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= 5) {
            throw new InvalidParamException("Number of images must be <= 5");
        }
        return productImageRepository.save(newProductImage);
    }
}
