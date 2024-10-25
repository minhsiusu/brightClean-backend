package com.example.brightClean.service.impl;

import com.example.brightClean.domain.Cart;
import com.example.brightClean.domain.CartItem;
import com.example.brightClean.domain.Product;
import com.example.brightClean.repository.CartRepository;
import com.example.brightClean.service.CartService;
import com.example.brightClean.service.CartItemService;
import com.example.brightClean.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceimpl implements CartService {

    @Autowired
    private CartRepository cartRepository; // 購物車的儲存層，用來操作購物車的資料庫操作

    @Autowired
    private ProductServiceimpl productServiceimpl; // 產品服務層，用來查詢產品資料

    @Autowired
    private CartItemService cartItemService; // 購物車項目的服務層，用來處理購物車項目的操作

    // 根據用戶 ID 查找購物車
    @Override
    public Optional<Cart> findCartByUserId(Integer userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public Cart addToCart(Integer userId, Integer cartId, Integer productId, Integer quantity, Double price)
            throws Exception {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found"));

        // 查找產品，若產品不存在則拋出例外，確保前端傳遞的 productId 在資料庫中有效
        Product product = productServiceimpl.getProduct(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        // 查找購物車中的商品，如果該商品不在購物車中，則創建新的購物車項目
        CartItem cartItem = cartItemService.findCartItemInCart(cart.getCartId(), product.getProductId()).orElse(null);
        // 如果cartItem為 null 代表購物車中沒有產品，則創建新的購物車項目
        if (cartItem == null) {
            // 創建cartItem(新的購物車內容)
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(quantity * product.getPrice());
            // 保存新的購物車項目
            cartItemService.createCartItem(cartItem);
            // 將新項目加入購物車
            cart.getCartItems().add(cartItem);
        } else {
            // 如果產品已經存在購物車中，更新數量和價格
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(cartItem.getQuantity() * product.getPrice());
            // 更新購物車項目
            cartItemService.createCartItem(cartItem);
        }
        // 計算購物車的總價格和總數量
        return calcCartTotal(userId); // 返回更新後的購物車
    }

    @Override
    public Cart calcCartTotal(Integer userId) throws Exception {
        // 根據用戶 ID 查找購物車
        Cart cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new Exception("Cart not found for user with ID: " + userId));
        int totalPrice = 0;
        int totalQuantity = 0;

        // 遍歷購物車中的所有項目，計算總價格和總數量
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getPrice(); // 累加每個項目的價格
            totalQuantity += cartItem.getQuantity(); // 累加每個項目的數量
        }

        // 設定購物車的總價格和總數量
        cart.setTotalPrice(totalPrice);
        cart.setTotalQuantity(totalQuantity);

        // 保存更新後的購物車並返回
        return cartRepository.save(cart);
    }

    @Override
    public Integer clearCart(Integer userId) throws Exception {
        // 根據用戶 ID 查找購物車
        Cart cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new Exception("Cart not found for user with ID: " + userId));
        int totalPrice = cart.getTotalPrice();
        cart.getCartItems().clear();
        // 重設購物車的總價格和總數量為 0
        cart.setTotalPrice(0);
        cart.setTotalQuantity(0);

        // 保存更新後的購物車
        cartRepository.save(cart);

        // 返回清空前的總價格
        return totalPrice;
    }
}