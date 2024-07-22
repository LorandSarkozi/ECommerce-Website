package com.example.demo.service;

import com.example.demo.dto.AnalyticsResponseDto;
import com.example.demo.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long orderId, String status);
    AnalyticsResponseDto calculateAnalytics();

}
