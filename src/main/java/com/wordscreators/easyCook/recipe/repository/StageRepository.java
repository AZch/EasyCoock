package com.wordscreators.easyCook.recipe.repository;

import com.wordscreators.easyCook.recipe.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
}
