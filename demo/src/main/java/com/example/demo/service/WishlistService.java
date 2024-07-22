package com.example.demo.service;

import com.example.demo.dto.WishlistDto;

import java.util.List;

public interface WishlistService {

    WishlistDto addProductToWishlist(WishlistDto wishlistDto);
    List<WishlistDto> getWishlistByUserId(Long userId);
}
