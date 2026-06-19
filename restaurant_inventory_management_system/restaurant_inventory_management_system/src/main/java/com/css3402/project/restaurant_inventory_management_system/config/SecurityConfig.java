package com.css3402.project.restaurant_inventory_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. SPECIFIC RULES FIRST
                        // Only Admins can modify the structure of ingredients
                        .requestMatchers("/ingredient/new", "/ingredient/edit/**", "/ingredient/delete/**").hasRole("ADMIN")

                        // Users and Admins can update stock and view dashboard
                        .requestMatchers("/ingredient/update-stock/**", "/dashboard").hasAnyRole("USER", "ADMIN")

                        // 2. PUBLIC PAGES NEXT
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll()

                        // 3. LASTLY, catch-all (Check everyone else)
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}