package com.example.ecommerce_backend.Controller;

import com.example.ecommerce_backend.Dtos.ProductDto;
import jakarta.validation.Valid;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("show Products");
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<String> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok("Product with Id " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", productId));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files=productDto.getFiles();
            files= files==null ? new ArrayList<MultipartFile>() : files;
            for(MultipartFile file : files){
                if (file != null) {
                    if(file.getSize()==0){
                        continue;
                    }
                    if (file.getSize() > 10 * 1024 * 1024) {// size >10mb
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large ! Maximun size is 10mb");
                    }
                    String contentType = file.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                    }
                    //Lưu file và cập nhật trong DTO
                    String filename=storeFile(file);
                    //TODO: lưu filename vào DB

                }
            }
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Thêm UUID vào trước tên file để đảm bảo unique
        String uniqueFileName= UUID.randomUUID().toString()+"_"+filename;
        // Đường dẫn đến thư mục lưu file
        Path uploadDir = Paths.get("uploads");
        //Kiểm tra tạo thư mục nếu không tồn tại
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Đường dẫn đến file
        Path destination = Paths.get(uploadDir.toString(),uniqueFileName);
        //Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok("edit product :" + id);
    }
}
