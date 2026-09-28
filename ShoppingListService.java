package com.example.recipebox.service;

import com.example.recipebox.entity.Ingredient;
import com.example.recipebox.entity.MealPlan;
import com.example.recipebox.repository.IngredientRepository;
import com.example.recipebox.repository.MealPlanRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingListService {

    private final MealPlanRepository mealPlanRepository;
    private final IngredientRepository ingredientRepository;

    public ShoppingListService(
            MealPlanRepository mealPlanRepository,
            IngredientRepository ingredientRepository) {

        this.mealPlanRepository = mealPlanRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Map<String, Object>> generateShoppingList() {

        Map<String, Double> quantities = new LinkedHashMap<>();
        Map<String, String> units = new LinkedHashMap<>();

        List<MealPlan> mealPlans = mealPlanRepository.findAll();

        for (MealPlan mealPlan : mealPlans) {

            Long recipeId = mealPlan.getRecipe().getId();

            List<Ingredient> ingredients =
                    ingredientRepository.findByRecipeId(recipeId);

            for (Ingredient ingredient : ingredients) {

                String name = ingredient.getName().toLowerCase();
                double quantity = ingredient.getQuantity();
                String unit = ingredient.getUnit();

                quantities.put(
                        name,
                        quantities.getOrDefault(name, 0.0)
                                + quantity
                );

                units.putIfAbsent(name, unit);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (String name : quantities.keySet()) {

            Map<String, Object> item = new LinkedHashMap<>();

            item.put("ingredient", name);
            item.put("quantity", quantities.get(name));
            item.put("unit", units.get(name));

            result.add(item);
        }

        return result;
    }
}