package com.example.brightClean.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.brightClean.domain.AuthResponse;
import com.example.brightClean.domain.Cart;
import com.example.brightClean.domain.User;
import com.example.brightClean.domain.params.LoginParam;
import com.example.brightClean.domain.params.UserParam;
import com.example.brightClean.exception.NotFoundException;
import com.example.brightClean.service.MailService;
import com.example.brightClean.service.UserService;
import com.example.brightClean.service.impl.CartService;
import com.example.brightClean.service.impl.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/user")
@Tag(name = "user")
public class UserController {

    @Autowired
    private UserService userservice;
    @Autowired
    private MailService mailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @GetMapping("/get")
    // public User getMethodName(@RequestParam("id") int id) {
    // return userservice.getUser(id).get();
    // }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> regist(@RequestBody @Valid User user) throws Exception {
        // TODO: process POST request
        // 使用JWTProvider產生token
        System.out.println(user);
        userservice.createUser(user);
        Cart cart = cartService.createCart(userservice.findByEmail(user.getEmail()).orElseThrow());
        String token = jwtService.generateToken(user.getEmail());

        // 設置 Cookie
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        responseBuilder.header("Set-Cookie",
                "jwt=" + token + "; HttpOnly; Secure; SameSite=Strict; Path=/; Max-Age=" + (24 * 60 * 60));

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Signup Success");
        authResponse.setAccount(user.getAccount());
        authResponse.setEmail(user.getEmail());

        return responseBuilder.body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginParam loginParam) {
        String email = loginParam.getEmail();

        String mismatchTip = "名稱或密碼不正確";
        System.out.println(email);
        System.out.println(loginParam.getPassword());

        User user = userservice.findByEmail(email).orElseThrow();
        // try {
        // // Get user by account
        // // user = userservice.getByAccountOfNonNull(account);

        // user = userservice.getByEmail(account).orElseThrow();
        // System.out.println(user);
        // } catch (NotFoundException e) {

        // throw new NotFoundException(mismatchTip);
        // }

        // if (!userservice.passwordMatch(user, loginParam.getPassword())) {

        // throw new NotFoundException(mismatchTip);
        // }
        AuthResponse authResponse = new AuthResponse();

        if (user == null || !passwordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
            new Exception("Invalid email or password");
            authResponse.setJwt(loginParam.getEmail());
            authResponse.setMessage("Login failed");

            System.out.println(loginParam.getEmail());
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }

        String token = jwtService.generateToken(user.getEmail());

        // 設置 Cookie
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        responseBuilder.header("Set-Cookie",
                "jwt=" + token + "; HttpOnly; Secure; SameSite=Strict; Path=/; Max-Age=" + (24 * 60 * 60));

        authResponse.setMessage("Login Success");
        authResponse.setAccount(user.getAccount());
        authResponse.setEmail(user.getEmail());

        return responseBuilder.body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        responseBuilder.header("Set-Cookie", "jwt=; HttpOnly; Secure; SameSite=Strict; Path=/; Max-Age=0");
        return responseBuilder.build();
    }

    @GetMapping("/getall")
    public List<User> findUsers() {
        return userservice.findUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<AuthResponse> postMethodName(@RequestBody User user) throws Exception {

        String token = jwtService.generateToken(user.getEmail());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");

        System.out.println(token);
        userservice.createUser(user);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/forget")
    public String getMethodName(@RequestParam("email") String email) {
        mailService.sendPlainText(email, "忘記密碼", "傳送信件 測試一下 4567");
        return "傳送成功";
    }

    @GetMapping("/jwt")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userservice.findUserByJWT(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/session")
    public ResponseEntity<AuthResponse> checkSession(@CookieValue(name = "jwt", required = false) String jwt) {
        AuthResponse authResponse = new AuthResponse();
        if (jwt == null || jwt.isEmpty()) {
            authResponse.setMessage("未登入");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }

        try {
            // 使用現有的 getEmailFromJWT 方法解析電子郵件
            String email = jwtService.getEmailFromJWT(jwt);
            User user = userservice.findByEmail(email).orElseThrow(() -> new NotFoundException("用戶未找到"));

            authResponse.setMessage("已登入");
            authResponse.setAccount(user.getAccount());
            authResponse.setEmail(user.getEmail());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            authResponse.setMessage("無效的 JWT");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
