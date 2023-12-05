package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.constants.WebConstant;
import com.example.ecommerce_backend.dtos.ProductDto;
import com.example.ecommerce_backend.dtos.ProductImageDto;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.models.ProductImage;
import com.example.ecommerce_backend.responses.ProductListResponse;
import com.example.ecommerce_backend.responses.ProductResponse;
import com.example.ecommerce_backend.services.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDto productDto,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDto);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ){
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > WebConstant.MAXIMUM_IMAGES) {
                return ResponseEntity.badRequest().body("Maximum "+WebConstant.MAXIMUM_IMAGES+" files are allowed");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDto.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
            String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
            // Đường dẫn đến thư mục mà bạn muốn lưu file

            java.nio.file.Path uploadDir = Paths.get("uploads");
            // Kiểm tra và tạo thư mục nếu nó không tồn tại
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            // Đường dẫn đầy đủ đến file
            java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
            // Sao chép file vào thư mục đích
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFilename;

    }
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(required = false,defaultValue = WebConstant.DEFAULT_PAGE_SIZE) Integer page,
            @RequestParam(required = false,defaultValue = WebConstant.DEFAULT_PAGE_SIZE_STR)  Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        List<ProductResponse> products=productPage.getContent();
        int totalPages= productPage.getTotalPages();
        return ResponseEntity.ok(ProductListResponse.builder().products(products).totalPages(totalPages).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable("id") Long productId
    ) {
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id,@RequestBody ProductDto productDto){
        try{
            Product product = productService.updateProduct(id,productDto);
            return ResponseEntity.ok(product);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Delete id "+id+" successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //@PostMapping("/generateFakeProducts")
    public ResponseEntity<String> generateFakeProducts(){
        Faker faker = new Faker();
        for (int i =0;i<100;i++){
            String productName=faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductDto productDto = ProductDto.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10,9000000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long)faker.number().numberBetween(1,4))
                    .build();
            try{
                productService.createProduct(productDto);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("fake product created successfully");
    }
}
