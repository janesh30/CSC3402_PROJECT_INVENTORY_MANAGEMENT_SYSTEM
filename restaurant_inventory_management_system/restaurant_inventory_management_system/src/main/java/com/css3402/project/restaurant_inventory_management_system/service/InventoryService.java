package com.css3402.project.restaurant_inventory_management_system.service;

import com.css3402.project.restaurant_inventory_management_system.model.Category;
import com.css3402.project.restaurant_inventory_management_system.model.Ingredient;
import com.css3402.project.restaurant_inventory_management_system.Repository.CategoryRepository;
import com.css3402.project.restaurant_inventory_management_system.Repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;

    public InventoryService(IngredientRepository ingredientRepository, CategoryRepository categoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
    }

    // --------------------------------------------------------
    // INGREDIENT CRUD ACTIONS
    // --------------------------------------------------------
    public List<Ingredient> searchIngredients(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return ingredientRepository.findByNameContainingIgnoreCase(keyword);
        }
        return ingredientRepository.findAll();
    }

    // RESTORED: Needed for the Excel Export in your Controller!
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public void saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    // --------------------------------------------------------
    // CATEGORY LOGIC
    // --------------------------------------------------------
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}