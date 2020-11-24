package com.wordscreators.easyCook;

import com.wordscreators.easyCook.recipe.controller.IngredientController;
import com.wordscreators.easyCook.recipe.controller.RecipeController;
import com.wordscreators.easyCook.recipe.controller.StageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EasycoockApplicationTests {

    @Autowired
    private IngredientController ingredientController;

    @Autowired
    private RecipeController recipeController;

    @Autowired
    private StageController stageController;

    @Test
    void contextLoads() {
        assertThat(ingredientController).isNotNull();
        assertThat(recipeController).isNotNull();
        assertThat(stageController).isNotNull();
    }

}
