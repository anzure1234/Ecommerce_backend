package com.example.ecommerce_backend.models;

import com.example.ecommerce_backend.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(length = 100)
    private String name;
    @Email
    private String email;
    @Column(name="phone_number",nullable = false)
    private String phoneNumber;
    @Column(nullable = false, length = 200)
    private String address;

    private String note;
    @Column(name="order_date")
    private Date orderDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name="total_money")
    private Float totalMoney;
    @Column(name="shipping_method")
    private String shippingMethod;
    @Column(name="payment_method")
    private String paymentMethod;
    @Column(name="shipping_address")
    private String shippingAddress;

    @Column(name="tracking_number")
    private String trackingNumber;
    @Column(name="shipping_date")
    private LocalDate shippingDate;

    private Boolean active;

}
