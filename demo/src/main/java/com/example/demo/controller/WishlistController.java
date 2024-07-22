package com.example.demo.controller;

import com.example.demo.dto.WishlistDto;
import com.example.demo.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {

        WishlistDto postedWishlistDto = wishlistService.addProductToWishlist(wishlistDto);
        if(postedWishlistDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postedWishlistDto);
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));

    }
}
