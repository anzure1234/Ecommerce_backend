package com.example.ecommerce_backend.Dtos;

import com.example.ecommerce_backend.Models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto {

    @JsonProperty("product_id")
    @Min(value = 1,message = "Product's id must be greater than 0")
    private Long productId;

    @Size(min = 1,max = 300, message = "Image's name")
    @Column(name="image_url")
    private String imageUrl;
}
