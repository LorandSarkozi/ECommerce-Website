package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderedProductsResponseDto {

    private List<ProductDto> productDtoList;

    private Double orderAmount;
}
