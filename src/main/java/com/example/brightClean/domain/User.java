package com.example.brightClean.domain; // 指定類別所在的包，這裡是 com.example.brightClean.domain

import jakarta.persistence.Column; // 引入 JPA 的 Column 註解，用來將類別屬性映射到資料庫欄位
import jakarta.persistence.Entity; // 引入 JPA 的 Entity 註解，表示這是一個 JPA 實體類
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // 引入 JPA 的 Id 註解，表示這是主鍵欄位
import jakarta.persistence.Table; // 引入 JPA 的 Table 註解，指定這個實體類對應的資料表名稱
import lombok.Data; // 引入 Lombok 的 Data 註解，自動生成 getter、setter、equals、hashCode、toString 方法

/**
 * 這個類別用來對應資料庫中的 "user" 表格，
 * 使用了 Lombok 簡化程式碼，以及 JPA 進行物件-關聯映射（ORM）。
 */
@Data // 使用 Lombok 來自動生成 getter、setter、equals、hashCode、toString 方法，避免重複編寫樣板程式碼
@Entity // JPA 的 Entity 註解，表示這個類是 JPA 的一個實體類，將對應資料庫中的表格
@Table(name = "user") // 指定對應資料庫的 "user" 表格名稱，如果不指定，JPA 預設會使用類別名稱
public class User {

    @Id // 表示這個欄位是資料表中的主鍵（Primary Key）
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // 對應資料庫中的 "name" 欄位
    private Integer id; // 使用 Integer 型別來表示使用者的唯一標識 ID，對應資料庫的主鍵

    @Column(name = "name") // 對應資料庫中的 "name" 欄位
    private String name; // 使用者的姓名

    @Column(name = "account") // 對應資料庫中的 "account" 欄位
    private String account; // 使用者的帳號名稱

    @Column(name = "password") // 對應資料庫中的 "password" 欄位
    private String password; // 使用者的登入密碼

    @Column(name = "email") // 對應資料庫中的 "email" 欄位
    private String email; // 使用者的電子郵件地址

    @Column(name = "cellphone") // 對應資料庫中的 "cellphone" 欄位
    private String cellphone; // 使用者的手機號碼

    @Column(name = "address") // 對應資料庫中的 "address" 欄位
    private String address; // 使用者的住址或聯絡地址

}