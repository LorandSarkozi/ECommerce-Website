package com.example.demo.service;

import com.example.demo.model.Coupon;

import java.util.List;

public interface CouponService {

    public Coupon createCoupon(Coupon coupon);

    public List<Coupon> getAllCoupons();
}
