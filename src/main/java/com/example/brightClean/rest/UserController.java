package com.example.brightClean.rest;

import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.brightClean.domain.Cart;
import com.example.brightClean.domain.User;
import com.example.brightClean.domain.params.LoginParam;
import com.example.brightClean.domain.params.UserParam;
import com.example.brightClean.exception.NotFoundException;
import com.example.brightClean.service.MailService;
import com.example.brightClean.service.UserService;
import com.example.brightClean.service.CartService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.brightClean.repository.CartRepository;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userservice;
    @Autowired
    private MailService mailService;

    @GetMapping("/get")
    public User getMethodName(@RequestParam("id") int id) {
        return userservice.getUser(id).get();
    }

    @PostMapping("/register")
    public ResponseEntity<String> regist(@RequestBody @Valid UserParam user) {
        // TODO: process POST request
        System.out.println(user);
        return ResponseEntity.ok("註冊成功");
    }

    @PostMapping("/login")
    @CrossOrigin("http://localhost:8080")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginParam loginParam) {
        String account = loginParam.getAccount();

        String mismatchTip = "名稱或密碼不正確";

        final User user;
        final Cart cart;

        try {
            // Get user by account
            user = userservice.getByAccountOfNonNull(account);
            if (!userservice.passwordMatch(user, loginParam.getPassword())) {

                throw new NotFoundException(mismatchTip);
            }
            cart = cartService.findCartByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Cart not found for user with ID: " + user.getId()));

        } catch (NotFoundException e) {

            throw new NotFoundException(mismatchTip);
        } catch (RuntimeException e) {
            // 捕獲所有其他異常
            throw new RuntimeException("登入過程中發生錯誤", e);
        }

        // 將用戶信息和購物車ID返回給前端
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("account", user.getAccount());
        response.put("email", user.getEmail());
        response.put("cartId", cart.getCartId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public List<User> getUsers() {
        return userservice.getUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<User> postMethodName(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(userservice.create(user));
    }

    @GetMapping("/forget")
    public String getMethodName(String param) {
        mailService.sendPlainText("rty4560000@gmail.com", "忘記密碼", "傳送信件 測試一下 4567");
        return "傳送成功";
    }

}
