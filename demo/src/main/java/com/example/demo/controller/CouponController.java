package com.example.demo.controller;


import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Coupon;
import com.example.demo.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;


    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try{
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);
        } catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
}
