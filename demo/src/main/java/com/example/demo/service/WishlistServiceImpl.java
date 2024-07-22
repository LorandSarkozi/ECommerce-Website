package com.example.demo.service;

import com.example.demo.dto.WishlistDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.Wishlist;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) {
        Optional<Product> product = productRepository.findById(wishlistDto.getProductId());
        Optional<User> user = userRepository.findById(wishlistDto.getUserId());

        if(product.isPresent() && user.isPresent()) {
            Wishlist wishlist = new Wishlist();
            wishlist.setProduct(product.get());
            wishlist.setUser(user.get());

            return wishlistRepository.save(wishlist).getWishlistDto();
        }
        return null;
    }

    public List<WishlistDto> getWishlistByUserId(Long userId) {
        System.out.println("Here");
        return wishlistRepository.findAllByUserId(userId).stream().map(Wishlist::getWishlistDto).collect(Collectors.toList());
    }



}
