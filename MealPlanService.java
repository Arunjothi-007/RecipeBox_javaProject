package com.example.recipebox.service;

import com.example.recipebox.entity.MealPlan;
import com.example.recipebox.entity.Recipe;
import com.example.recipebox.entity.User;
import com.example.recipebox.repository.MealPlanRepository;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public MealPlanService(
            MealPlanRepository mealPlanRepository,
            UserRepository userRepository,
            RecipeRepository recipeRepository) {

        this.mealPlanRepository = mealPlanRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public MealPlan addMealPlan(
            MealPlan mealPlan,
            Long userId,
            Long recipeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: " + userId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recipe not found with ID: " + recipeId));

        mealPlan.setUser(user);
        mealPlan.setRecipe(recipe);

        return mealPlanRepository.save(mealPlan);
    }

    public List<MealPlan> getAllMealPlans() {
        return mealPlanRepository.findAll();
    }

    public List<MealPlan> getMealPlansByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException(
                    "User not found with ID: " + userId);
        }

        return mealPlanRepository.findByUserId(userId);
    }

    public void deleteMealPlan(Long id) {

        if (!mealPlanRepository.existsById(id)) {
            throw new RuntimeException(
                    "Meal plan not found with ID: " + id);
        }

        mealPlanRepository.deleteById(id);
    }
}