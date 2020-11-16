package com.wordscreators.easyCook.recipe.repository;

import com.wordscreators.easyCook.recipe.model.Recipe;
import com.wordscreators.easyCook.recipe.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findStagesByRecipeAndIsDeleted(Recipe recipe, boolean isDeleted);

    Optional<Stage> findStageByIdAndIsDeleted(Long id, boolean isDeleted);
}
