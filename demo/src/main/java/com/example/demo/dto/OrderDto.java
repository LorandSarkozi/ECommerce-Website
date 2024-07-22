package com.example.demo.dto;

import com.example.demo.constants.OrderStatus;
import com.example.demo.model.CartItems;
import com.example.demo.model.User;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@XmlRootElement
public class OrderDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Double amount;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Double totalAmount;

    private Long discount;

    private UUID trackingId;


    private String userName;

    private List<CartItemsDto> cartItems;

    private String couponName;
}
