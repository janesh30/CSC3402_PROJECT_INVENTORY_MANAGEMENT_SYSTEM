package com.css3402.project.restaurant_inventory_management_system.Repository;

import com.css3402.project.restaurant_inventory_management_system.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Spring Boot automatically writes the SQL for this based on the method name!
    java.util.List<Ingredient> findByNameContainingIgnoreCase(String keyword);
}