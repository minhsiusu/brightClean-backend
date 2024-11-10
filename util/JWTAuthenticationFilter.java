package com.example.brightClean.util;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = null;

        // 從 Cookie 中提取 JWT
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        System.out.println("JWT from Cookie: " + jwt); // 調試輸出

        if (jwt != null) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRET.getBytes());
                JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

                Claims claims = parser.parseClaimsJws(jwt).getBody();
                String email = String.valueOf(claims.get("email"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("JWT Valid, email extracted: " + email); // 調試輸出
            } catch (Exception e) {
                System.out.println("JWT Validation Error: " + e.getMessage()); // 捕捉並顯示解析異常
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT or expired");
                return;
            }
        } else {
            System.out.println("No JWT in Cookie or Incorrect Cookie Format"); // 調試輸出
        }

        filterChain.doFilter(request, response);
    }
}