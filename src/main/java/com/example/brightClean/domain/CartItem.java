package com.example.brightClean.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data // 使用Lombok自動產生 getter 和 setter 方法
@Entity
@Table(name = "cart_item") // 指定對應資料庫的 "cart_item" 表格名稱，如果不指定，JPA 預設會使用類別名稱
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 對應資料庫的 "cart_item_id" 欄位
    @Column(name = "cart_item_id")
    private int cartItemId;

    // 關聯到 Product 表，表示購物車中的某個商品
    @ManyToOne
    // 指定了 product_id 作為外鍵 對應資料庫的 "product_id" 欄位
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 關聯到 Cart 表，表示這個商品屬於某個購物車
    @ManyToOne
    // 指定了cart_id 作為外鍵 對應資料庫的 "cart_id" 欄位
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // 對應資料庫的 "price" 欄位
    @Column(name = "price", nullable = false)
    private double price;

    // 對應資料庫的 "quantity" 欄位
    @Column(name = "quantity", nullable = false)
    private int quantity;

}
