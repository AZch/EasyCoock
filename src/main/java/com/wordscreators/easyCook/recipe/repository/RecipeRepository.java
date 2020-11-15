package com.wordscreators.easyCook.recipe.repository;

import com.wordscreators.easyCook.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
