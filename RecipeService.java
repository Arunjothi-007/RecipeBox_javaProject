package com.example.recipebox.service;

import com.example.recipebox.entity.Cuisine;
import com.example.recipebox.entity.Recipe;
import com.example.recipebox.entity.User;
import com.example.recipebox.repository.CuisineRepository;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CuisineRepository cuisineRepository;

    public RecipeService(
            RecipeRepository recipeRepository,
            UserRepository userRepository,
            CuisineRepository cuisineRepository) {

        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.cuisineRepository = cuisineRepository;
    }

    public Recipe addRecipe(
            Recipe recipe,
            Long userId,
            Long cuisineId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: " + userId));

        Cuisine cuisine = cuisineRepository.findById(cuisineId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cuisine not found with ID: " + cuisineId));

        recipe.setUser(user);
        recipe.setCuisine(cuisine);

        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recipe not found with ID: " + id));
    }

    public List<Recipe> searchRecipes(String keyword) {
        return recipeRepository.searchRecipes(keyword);
    }

    public List<Recipe> getFavouriteRecipes() {
        return recipeRepository.findByFavouriteTrue();
    }

    public Recipe toggleFavourite(Long id) {

        Recipe recipe = getRecipeById(id);

        recipe.setFavourite(!recipe.isFavourite());

        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(
            Long id,
            Recipe updatedRecipe,
            Long userId,
            Long cuisineId) {

        Recipe recipe = getRecipeById(id);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: " + userId));

        Cuisine cuisine = cuisineRepository.findById(cuisineId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cuisine not found with ID: " + cuisineId));

        recipe.setName(updatedRecipe.getName());
        recipe.setSteps(updatedRecipe.getSteps());
        recipe.setPrepTime(updatedRecipe.getPrepTime());
        recipe.setUser(user);
        recipe.setCuisine(cuisine);

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {

        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException(
                    "Recipe not found with ID: " + id);
        }

        recipeRepository.deleteById(id);
    }
}