package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="image_url",length = 300)
    private String imageUrl;


}
