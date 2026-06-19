package com.css3402.project.restaurant_inventory_management_system.service;

import com.css3402.project.restaurant_inventory_management_system.model.Category;
import com.css3402.project.restaurant_inventory_management_system.model.Ingredient;
import com.css3402.project.restaurant_inventory_management_system.model.Vendor;
import com.css3402.project.restaurant_inventory_management_system.Repository.CategoryRepository;
import com.css3402.project.restaurant_inventory_management_system.Repository.IngredientRepository;
import com.css3402.project.restaurant_inventory_management_system.Repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public InventoryService(IngredientRepository ingredientRepository, CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    // --------------------------------------------------------
    // INGREDIENT CRUD ACTIONS
    // --------------------------------------------------------
    public List<Ingredient> searchIngredients(String keyword, Long categoryId) {
        // If the search box is completely empty, set it to null so our smart query ignores it
        if (keyword != null && keyword.trim().isEmpty()) {
            keyword = null;
        }
        return ingredientRepository.searchByKeywordAndCategory(keyword, categoryId);
    }

    // RESTORED: Needed for the Excel Export in your Controller!
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public java.util.List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
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