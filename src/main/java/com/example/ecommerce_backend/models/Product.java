package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name="category_id")
    private Category categoryId;

}
