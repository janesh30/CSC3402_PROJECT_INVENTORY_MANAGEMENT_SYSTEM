package com.css3402.project.restaurant_inventory_management_system.controller;

import com.css3402.project.restaurant_inventory_management_system.model.Ingredient;
import com.css3402.project.restaurant_inventory_management_system.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // ----------------------------------------------------
    // UPDATED: Now includes the Search Bar logic!
    // ----------------------------------------------------
    @GetMapping("/dashboard")
    public String viewDashboard(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        // 1. Fetch the searched items (or all items)
        java.util.List<Ingredient> ingredients = inventoryService.searchIngredients(keyword);
        model.addAttribute("ingredients", ingredients);

        // 2. Safely calculate chart numbers in Java (No Infinite Loops!)
        long lowCount = 0;
        long optimalCount = 0;

        for (Ingredient ing : ingredients) {
            if (ing.getQuantity().compareTo(ing.getMinimumStock()) <= 0) {
                lowCount++;
            } else {
                optimalCount++;
            }
        }

        // 3. Send just the raw numbers to the HTML
        model.addAttribute("lowStockCount", lowCount);
        model.addAttribute("optimalStockCount", optimalCount);

        return "dashboard";
    }

    @GetMapping("/ingredient/new")
    public String showNewIngredientForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("categories", inventoryService.getAllCategories());
        return "add-ingredient";
    }

    @PostMapping("/ingredient/save")
    public String saveIngredient(@ModelAttribute("ingredient") Ingredient ingredient) {
        inventoryService.saveIngredient(ingredient);
        return "redirect:/dashboard";
    }

    @GetMapping("/ingredient/edit/{id}")
    public String showEditIngredientForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingredient", inventoryService.getIngredientById(id));
        model.addAttribute("categories", inventoryService.getAllCategories());
        return "edit-ingredient";
    }

    @GetMapping("/ingredient/delete/{id}")
    public String deleteIngredient(@PathVariable("id") Long id) {
        inventoryService.deleteIngredient(id);
        return "redirect:/dashboard";
    }

    // Show the restricted update-stock form
    @GetMapping("/ingredient/update-stock/{id}")
    public String showUpdateStockForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingredient", inventoryService.getIngredientById(id));
        return "update-stock";
    }

    // Save ONLY the new quantity
    @PostMapping("/ingredient/update-stock")
    public String saveStockUpdate(@RequestParam("id") Long id, @RequestParam("quantity") java.math.BigDecimal newQuantity) {
        Ingredient existingIngredient = inventoryService.getIngredientById(id);
        if (existingIngredient != null) {
            existingIngredient.setQuantity(newQuantity);
            inventoryService.saveIngredient(existingIngredient);
        }
        return "redirect:/dashboard";
    }

    // ----------------------------------------------------
    // EXPORT TO EXCEL
    // ----------------------------------------------------
    @GetMapping("/ingredient/export")
    public void exportToCSV(jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"inventory_report.csv\"");

        java.io.PrintWriter writer = response.getWriter();
        writer.println("ID,Ingredient Name,Category,Quantity,Unit,Status"); // Excel Headers

        for (Ingredient ing : inventoryService.getAllIngredients()) {
            String status = ing.getQuantity().compareTo(ing.getMinimumStock()) <= 0 ? "LOW STOCK" : "Optimal";
            writer.println(ing.getId() + "," + ing.getName() + "," + ing.getCategory().getName() + ","
                    + ing.getQuantity() + "," + ing.getUnitOfMeasure() + "," + status);
        }
    }
}