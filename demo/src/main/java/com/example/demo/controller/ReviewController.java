package com.example.demo.controller;

import com.example.demo.dto.OrderedProductsResponseDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProductsDetailsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(reviewService.getOrderedProductsDetailsByOrderId(orderId));
    }


    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto review = reviewService.giveReview(reviewDto);
        if(review == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(review);

    }
}
