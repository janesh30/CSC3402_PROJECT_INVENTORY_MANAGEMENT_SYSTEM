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
                .csrf(csrf -> csrf.disable()) // <-- THIS FIXES LOGOUT AND HTML FORMS!
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/css/**", "/js/**").permitAll()
                        // ONLY Admins can do full edits, deletes, and create new items
                        .requestMatchers("/ingredient/new", "/ingredient/edit/**", "/ingredient/delete/**").hasRole("ADMIN")
                        // BOTH Users and Admins can update current stock levels
                        .requestMatchers("/ingredient/update-stock/**").hasAnyRole("USER", "ADMIN")
                        // BOTH Users and Admins can see the dashboard
                        .requestMatchers("/dashboard").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Standard logout URL
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}