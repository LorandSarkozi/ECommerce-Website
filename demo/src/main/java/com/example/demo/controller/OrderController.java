package com.example.demo.controller;


import com.example.demo.dto.AnalyticsResponseDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        return ResponseEntity.ok(orderService.getAllPlacedOrders());
    }

    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        OrderDto orderDto = orderService.changeOrderStatus(orderId, status);
        if(orderDto == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/order/analytics")
    public ResponseEntity<AnalyticsResponseDto> getAnalytics(){
        return ResponseEntity.ok(orderService.calculateAnalytics());
    }
}
