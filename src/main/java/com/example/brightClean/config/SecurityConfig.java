// package com.example.brightClean.config;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// import com.example.brightClean.service.impl.JwtService;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//         return httpSecurity
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
//                 .build();
//     }

//     @Bean
//     public UserDetailsService inMemoryUserDetailsManager() {
//         UserDetails user = User
//                 .withUsername("user1")
//                 .password("111")
//                 .authorities("STUDENT", "ASSISTANT")
//                 .build();
//         return new InMemoryUserDetailsManager(List.of(user));
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return NoOpPasswordEncoder.getInstance();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//         return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
//     }

//     @Bean
//     public JwtService jwtService(
//             @Value("${jwt.secret-key}") String secretKeyStr,
//             @Value("${jwt.valid-seconds}") int validSeconds
//     ) {
//         return new JwtService(secretKeyStr, validSeconds);
//     }
// }