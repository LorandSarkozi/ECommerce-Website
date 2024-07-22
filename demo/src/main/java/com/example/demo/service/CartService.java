package com.example.demo.service;

import com.example.demo.dto.AddProductInCartDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getMyPlacedOrders(Long userId);
    OrderDto searchOrderByTrackingId(UUID trackingId);
    String exportOrderDetails(UUID trackingId, String fileType);
    String convertToXml(OrderDto order);


}
