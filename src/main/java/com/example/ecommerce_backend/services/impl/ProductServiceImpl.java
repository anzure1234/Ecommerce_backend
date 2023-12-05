package com.example.ecommerce_backend.services.impl;

import com.example.ecommerce_backend.constants.WebConstant;
import com.example.ecommerce_backend.dtos.ProductDto;
import com.example.ecommerce_backend.dtos.ProductImageDto;
import com.example.ecommerce_backend.models.Category;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.models.ProductImage;
import com.example.ecommerce_backend.repositories.CategoryRepository;
import com.example.ecommerce_backend.repositories.ProductImageRepository;
import com.example.ecommerce_backend.repositories.ProductRepository;
import com.example.ecommerce_backend.responses.ProductResponse;
import com.example.ecommerce_backend.services.ProductService;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.exceptions.InvalidParamException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
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
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        Category existingCategory = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: "+productDto.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .category(existingCategory)
                .description(productDto.getDescription())
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
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(long id, ProductDto productDto)
            throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            //copy các thuộc tính từ DTO -> Product
            Category existingCategory = categoryRepository
                    .findById(productDto.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: "+productDto.getCategoryId()));
            existingProduct.setName(productDto.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setThumbnail(productDto.getThumbnail());
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
            ProductImageDto productImageDto) throws Exception {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+productImageDto.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDto.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= WebConstant.MAXIMUM_IMAGES) {
            throw new InvalidParamException("Number of images must be <="+WebConstant.MAXIMUM_IMAGES);
        }
        return productImageRepository.save(newProductImage);
    }
}
