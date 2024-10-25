package com.example.brightClean.domain; // 指定類別所在的包，這裡是 com.example.brightClean.domain

import jakarta.persistence.Column; // 引入 JPA 的 Column 註解，用來將類別屬性映射到資料庫欄位
import jakarta.persistence.Entity; // 引入 JPA 的 Entity 註解，表示這是一個 JPA 實體類
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // 引入 JPA 的 Id 註解，表示這是主鍵欄位
import jakarta.persistence.Table; // 引入 JPA 的 Table 註解，指定這個實體類對應的資料表名稱
import lombok.Data; // 引入 Lombok 的 Data 註解，自動生成 getter、setter、equals、hashCode、toString 方法

@Data // 使用 Lombok 來自動生成 getter、setter、equals、hashCode、toString 方法，避免重複編寫樣板程式碼
@Entity // JPA 的 Entity 註解，表示這個類是 JPA 的一個實體類，將對應資料庫中的表格
@Table(name = "product") // 指定對應資料庫的 "product" 表格名稱，如果不指定，JPA 預設會使用類別名稱
public class Product {

    @Id // 表示這個欄位是資料表中的主鍵（Primary Key） 對應資料庫的 "product_id" 欄位
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    // 對應資料庫中的 "product_name" 欄位
    @Column(name = "product_name")
    private String productName;

    // 對應資料庫中的 "price" 欄位
    @Column(name = "price")
    private double price;

    // 對應資料庫中的 "count" 欄位
    @Column(name = "count")
    private int count;

    // 對應資料庫中的 "type" 欄位
    @Column(name = "type")
    private String type;

    // 對應資料庫中的 "picture" 欄位
    @Column(name = "picture")
    private String picture;

    // 對應資料庫中的 "detail" 欄位
    @Column(name = "detail")
    private String detail;

    // 對應資料庫中的 "sales" 欄位
    @Column(name = "sales")
    private int sales;
}
