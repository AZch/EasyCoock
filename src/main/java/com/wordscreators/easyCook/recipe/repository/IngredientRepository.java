package com.wordscreators.easyCook.recipe.repository;

import com.wordscreators.easyCook.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
