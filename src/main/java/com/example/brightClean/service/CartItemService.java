package com.example.brightClean.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.brightClean.domain.CartItem;
import com.example.brightClean.domain.User;
import com.example.brightClean.repository.CartItemRepository;
import com.example.brightClean.service.impl.UserServiceimpl;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserServiceimpl userServiceImpl;

    // 查找購物車中的商品項目
    public Optional<CartItem> findCartItemInCart(Integer cartId, Integer productId) {
        return Optional.ofNullable(cartItemRepository.findByCartCartIdAndProductProductId(cartId, productId));
    }

    // 創建新的購物車項目
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(Math.max(cartItem.getQuantity(), 1));
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    // 更新購物車項目
    public CartItem updateCartItem(Integer userId, Integer id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);
        User user = userServiceImpl.getUser(item.getCart().getUser().getId())
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            return cartItemRepository.save(item);
        } else {
            throw new Exception("Unauthorized action. User ID does not match.");
        }
    }

    // 查找購物車項目通過ID
    public CartItem findCartItemById(Integer id) throws Exception {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception("CartItem not found with id: " + id));
    }

    // 根據 cartItemId 移除購物車項目
    public void removeCartItem(Integer cartItemId) {
        // 從數據庫中刪除購物車項目
        cartItemRepository.deleteById(cartItemId);
    }
}
