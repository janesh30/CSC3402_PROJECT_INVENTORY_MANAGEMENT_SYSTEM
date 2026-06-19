package com.css3402.project.restaurant_inventory_management_system.Repository;

import com.css3402.project.restaurant_inventory_management_system.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // This smart query checks if the keyword/category is null. If it is, it skips that filter!
    @Query("SELECT i FROM Ingredient i WHERE " +
            "(:keyword IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:categoryId IS NULL OR i.category.id = :categoryId)")
    List<Ingredient> searchByKeywordAndCategory(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

}