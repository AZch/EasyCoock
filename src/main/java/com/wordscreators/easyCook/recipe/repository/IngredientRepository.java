package com.wordscreators.easyCook.recipe.repository;

import com.wordscreators.easyCook.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findIngredientsByIsDeleted(boolean isDeleted);

    Optional<Ingredient> findIngredientByIdAndIsDeleted(Long id, boolean isDeleted);
}
