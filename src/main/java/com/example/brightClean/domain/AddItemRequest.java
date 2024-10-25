package com.example.brightClean.domain;

public class AddItemRequest {
    // 從前端獲取的變數有
    // userId
    // cartId
    // productId
    // quantity
    // price

    private Integer userId;
    private Integer cartId; // 使用者沒有的話要新增
    private Integer productId;
    private Integer quantity;
    private Double price;

    /* Getters 和 Setters userId */
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /* Getters 和 Setters cartId */
    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    /* Getters 和 Setters productId */
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /* Getters 和 Setters quantity */
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /* Getters 和 Setters price */
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
