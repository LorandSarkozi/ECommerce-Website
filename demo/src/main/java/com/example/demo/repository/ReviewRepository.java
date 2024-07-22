package com.example.demo.repository;

import com.example.demo.model.FAQ;
import com.example.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long productId);
}
