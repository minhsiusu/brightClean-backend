package com.example.brightClean.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.brightClean.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

  CartItem findByCartCartIdAndProductProductId(int cartId, int productId);

}