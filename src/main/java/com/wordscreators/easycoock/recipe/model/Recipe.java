package com.wordscreators.easycoock.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Recipe {
    private int id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Stage> preparationStages;

    public boolean validateIngredientsForStages() {
        return false;
    }
}
