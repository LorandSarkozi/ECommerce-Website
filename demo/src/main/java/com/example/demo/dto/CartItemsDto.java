package com.example.demo.dto;

import lombok.Data;

@Data
public class CartItemsDto {

    private Long id;

    private Double price;

    private Long quantity;

    private Long productId;

    private Long orderId;

    private String productName;

    private byte[] returnedImg;

    private Long userId;
}
