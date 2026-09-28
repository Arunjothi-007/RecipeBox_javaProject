package com.example.recipebox.repository;

import com.example.recipebox.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("""
        SELECT DISTINCT r
        FROM Recipe r
        JOIN r.cuisine c
        LEFT JOIN Ingredient i ON i.recipe.id = r.id
        WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Recipe> searchRecipes(@Param("keyword") String keyword);

    List<Recipe> findByFavouriteTrue();
}