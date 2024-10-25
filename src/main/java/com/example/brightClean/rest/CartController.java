package com.example.brightClean.rest;

import com.example.brightClean.domain.AddItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.brightClean.domain.Cart;
import com.example.brightClean.service.CartService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/cart")
@CrossOrigin("http://localhost:8080")

public class CartController {

    @Autowired
    private CartService cartService;

    // 從前端獲取的變數有
    // userId
    // cartId
    // productId
    // quantity
    // price
    @PostMapping("/addCart")
    public ResponseEntity<Cart> addToCart(@RequestBody AddItemRequest addItemRequest) {
        try {

            // 從 AddItemRequest 中獲取 userId, cartId, productId, quantity 和 price
            Integer userId = addItemRequest.getUserId();
            Integer cartId = addItemRequest.getCartId();
            Integer productId = addItemRequest.getProductId();
            Integer quantity = addItemRequest.getQuantity();
            Double price = addItemRequest.getPrice();

            // 調用服務層來處理加入購物車的邏輯
            Cart updatedCart = cartService.addToCart(userId, cartId, productId, quantity, price);
            return ResponseEntity.ok(updatedCart); // 返回更新後的購物車

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 如果出錯，返回 400 狀態碼
        }
    }
}
