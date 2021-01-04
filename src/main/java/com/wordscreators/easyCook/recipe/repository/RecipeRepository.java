package com.wordscreators.easyCook.recipe.repository;

import com.wordscreators.easyCook.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findRecipesByIsDeleted(boolean isDeleted);

    Optional<Recipe> findRecipeByIdAndIsDeleted(Long id, boolean isDeleted);
}
