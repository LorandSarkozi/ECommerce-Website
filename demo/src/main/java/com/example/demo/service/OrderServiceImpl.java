package com.example.demo.service;


import com.example.demo.constants.FileType;
import com.example.demo.constants.OrderStatus;
import com.example.demo.dto.AnalyticsResponseDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.exporter.FileExporter;
import com.example.demo.exporter.XMLFileExporter;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrders(){

        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if(Objects.equals(status,"Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            }else if(Objects.equals(status,"Delivered")){
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

    public AnalyticsResponseDto calculateAnalytics(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthsDate = currentDate.minusMonths(1);

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMothOrders = getTotalOrdersForMonth(previousMonthsDate.getMonthValue(), previousMonthsDate.getYear());

        Double currentMothEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Double previousMonthEarnings = getTotalEarningsForMonth(previousMonthsDate.getMonthValue(), previousMonthsDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.Shipped);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponseDto(placed,shipped,delivered, currentMonthOrders, previousMothOrders,
                currentMothEarnings, previousMonthEarnings);
    }

    public Long getTotalOrdersForMonth(int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        return (long)orders.size();


    }

    public Double getTotalEarningsForMonth(int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        Double sum = 0D;

        for ( Order order : orders ){
            sum+= order.getAmount();
        }
        return sum;
    }


}
