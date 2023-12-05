package com.example.ecommerce_backend.responses;

import com.example.ecommerce_backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends BaseResponse{
    private Long id;
    @JsonProperty("user_id")
    private Long userId;

    private String name;

    private String email;

    private String address;

    private String note;
    @JsonProperty("order_date")
    private LocalDateTime orderDate;
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String status;

    private Float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("shipping_address")
    private String shippingAddress;


    @Column(name="tracking_number")
    private String trackingNumber;
    @Column(name="shipping_date")
    private Date shippingDate;

    private Boolean active;

}
