package com.example.demo.repository;

import com.example.demo.constants.OrderStatus;
import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    Optional<Order> findByTrackingId(UUID trackingId);

    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatus);

    List<Order> findByDateBetweenAndOrderStatus(Date startDate, Date endDate, OrderStatus orderStatus);

    Long countByOrderStatus(OrderStatus orderStatus);


}
