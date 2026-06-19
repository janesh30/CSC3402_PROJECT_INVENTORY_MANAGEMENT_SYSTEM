package com.css3402.project.restaurant_inventory_management_system.Repository;

import com.css3402.project.restaurant_inventory_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}