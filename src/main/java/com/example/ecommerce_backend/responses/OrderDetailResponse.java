package com.example.ecommerce_backend.responses;

import com.example.ecommerce_backend.models.Order;
import com.example.ecommerce_backend.models.OrderDetail;
import com.example.ecommerce_backend.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private Long id;
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("product_id")
    private Long productId;

    private Float price;
    @JsonProperty("number_of_products")
    private Integer numberOfProducts;
    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;
    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
    }
    public static List<OrderDetailResponse> fromOrderDetailList(List<OrderDetail> orderDetails){
        return orderDetails.stream().map(OrderDetailResponse::fromOrderDetail).collect(Collectors.toList());
    }
}
