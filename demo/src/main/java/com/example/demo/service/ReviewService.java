package com.example.demo.service;

import com.example.demo.dto.OrderedProductsResponseDto;
import com.example.demo.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
