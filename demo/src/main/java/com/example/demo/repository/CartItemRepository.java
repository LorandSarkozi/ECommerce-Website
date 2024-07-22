
package com.example.demo.repository;


import com.example.demo.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
    Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long productId, Long orderId, Long userId);

}


