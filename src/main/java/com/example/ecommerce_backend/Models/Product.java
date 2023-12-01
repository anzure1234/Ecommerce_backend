package com.example.ecommerce_backend.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 350)
    private String name;

    private Float price;
    @Column(length = 300)
    private String thumbnail;
    @Column
    private String description;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category categoryId;

}
