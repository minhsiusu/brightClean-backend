package com.example.brightClean.service.impl;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import com.example.brightClean.util.JWTConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // private final SecretKey secretKey;
    // private final int validSeconds;
    // private final JwtParser jwtParser;

    // public JwtService(String secretKeyStr, int validSeconds) {
    // this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
    // this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    // this.validSeconds = validSeconds;
    // }

    // public String createLoginAccessToken(MemberUserDetails user) {
    // var expirationMillis = Instant.now()
    // .plusSeconds(validSeconds)
    // .getEpochSecond()
    // * 1000;

    // var claims = Jwts.claims()
    // .subject(user.getId())
    // .issuedAt(new Date())
    // .expiration(new Date(expirationMillis))
    // .add("nickname", user.getNickname())
    // .add("username", user.getUsername())
    // .add("authorities", user.getMemberAuthorities())
    // .build();

    // return Jwts.builder()
    // .claims(claims)
    // .signWith(secretKey)
    // .compact();
    // }

    // public Claims parseToken(String jwt) throws JwtException {
    // return jwtParser.parseSignedClaims(jwt).getPayload();
    // }
    SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRET != null ? JWTConstant.SECRET.getBytes() : null);

    public String generateToken(String email) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 12 * 60 * 60 * 1000))
                .claim("email", email)
                .signWith(key)
                .compact();
    }

    public String getEmailFromJWT(String jwt) {
        try {
            // 驗證並移除 "Bearer " 前綴
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7); // 移除 "Bearer " 前綴
            }

            // 解析 JWT 並獲取聲明
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            // 返回電子郵件
            return claims.get("email", String.class);
        } catch (Exception e) {
            System.out.println("JWT Parsing Error: " + e.getMessage());
            throw new RuntimeException("Invalid JWT");
        }
    }

}
