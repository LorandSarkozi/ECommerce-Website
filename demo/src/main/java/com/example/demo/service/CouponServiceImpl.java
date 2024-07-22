package com.example.demo.service;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon already exists");
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}
