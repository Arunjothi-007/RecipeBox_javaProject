package com.example.recipebox.service;

import com.example.recipebox.entity.Ingredient;
import com.example.recipebox.entity.Recipe;
import com.example.recipebox.repository.IngredientRepository;
import com.example.recipebox.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientService(
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository) {

        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public Ingredient addIngredient(
            Ingredient ingredient,
            Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recipe not found with ID: " + recipeId));

        ingredient.setRecipe(recipe);

        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getIngredientsByRecipe(Long recipeId) {

        if (!recipeRepository.existsById(recipeId)) {
            throw new RuntimeException(
                    "Recipe not found with ID: " + recipeId);
        }

        return ingredientRepository.findByRecipeId(recipeId);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public void deleteIngredient(Long id) {

        if (!ingredientRepository.existsById(id)) {
            throw new RuntimeException(
                    "Ingredient not found with ID: " + id);
        }

        ingredientRepository.deleteById(id);
    }
}