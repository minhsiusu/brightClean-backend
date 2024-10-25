package com.example.brightClean.service;

import com.example.brightClean.domain.Cart;
import java.util.Optional;

public interface CartService {
    Optional<Cart> findCartByUserId(Integer userId); // 查找用戶購物車

    Cart addToCart(Integer userId, Integer cartId, Integer productId, Integer quantity, Double price) throws Exception;

    Cart calcCartTotal(Integer userId) throws Exception;

    Integer clearCart(Integer userId) throws Exception;
}
