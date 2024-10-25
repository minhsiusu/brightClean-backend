package com.example.brightClean.domain;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data // 使用Lombok自動產生 getter 和 setter 方法
@Entity
@Table(name = "cart") // 指定對應資料庫的 "cart" 表格名稱，如果不指定，JPA 預設會使用類別名稱
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id") // 對應資料庫的 "cart_id" 欄位
    private Integer cartId;

    // 表示每個購物車只能關聯到一個用戶
    @OneToOne
    // 指定了 user_id 作為外鍵 對應資料庫的 "user_id" 欄位
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    // 表示一個購物車能擁有多個購物車內容
    // cascade = CascadeType.ALL 和 orphanRemoval = true。當購物車被刪除時，對應的購物車項目也會被刪除。
    // mappedBy = "cart" 表示在 CartItem 表中會有一個 cart 字段來對應到 Cart。
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    // 對應資料庫的 "price" 欄位
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;
    // 對應資料庫的 "price" 欄位
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

}
