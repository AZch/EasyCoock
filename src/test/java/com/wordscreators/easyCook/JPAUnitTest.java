package com.wordscreators.easyCook;

import com.wordscreators.easyCook.recipe.model.*;
import com.wordscreators.easyCook.recipe.repository.IngredientRepository;
import com.wordscreators.easyCook.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class JPAUnitTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void shouldFindNoRecipesIfRepositoryIsEmptyTest() {
        Iterable<Recipe> recipes = recipeRepository.findAll();

        assertThat(recipes).isEmpty();
    }

    @Test
    public void shouldCreateIngredient() {
        Ingredient ingredient = Ingredient.builder()
                .name("test ingredient")
                .valueCount(ValueType.GRAM)
                .valueSize(100)
                .calories(50)
                .sugar(51)
                .fat(52)
                .salt(53)
                .description("best ingredient of the best")
                .build();
        Ingredient saved = ingredientRepository.save(ingredient);
        assertThat(saved).hasFieldOrPropertyWithValue("name", "test ingredient");
        assertThat(saved).hasFieldOrPropertyWithValue("valueCount", ValueType.GRAM);
        assertThat(saved).hasFieldOrPropertyWithValue("valueSize", 100);
        assertThat(saved).hasFieldOrPropertyWithValue("calories", 50f);
        assertThat(saved).hasFieldOrPropertyWithValue("sugar", 51f);
        assertThat(saved).hasFieldOrPropertyWithValue("fat", 52f);
        assertThat(saved).hasFieldOrPropertyWithValue("salt", 53f);
        assertThat(saved).hasFieldOrPropertyWithValue("description", "best ingredient of the best");
    }

    @Test
    void shouldCreateRecipe() {
        Ingredient ingredient1 = Ingredient.builder()
                .name("test ingredient 1")
                .valueCount(ValueType.GRAM)
                .valueSize(100)
                .calories(50)
                .sugar(51)
                .fat(52)
                .salt(53)
                .description("best ingredient 1 of the best")
                .build();
        Ingredient ingredient2 = Ingredient.builder()
                .name("test ingredient 2")
                .valueCount(ValueType.GRAM)
                .valueSize(100)
                .calories(60)
                .sugar(61)
                .fat(62)
                .salt(63)
                .description("best ingredient 2 of the best")
                .build();
        Ingredient ingredient3 = Ingredient.builder()
                .name("test ingredient 3")
                .valueCount(ValueType.KILOGRAMM)
                .valueSize(1)
                .calories(100)
                .sugar(101)
                .fat(102)
                .salt(103)
                .description("best ingredient 3 of the best")
                .build();
        ingredientRepository.saveAll(Arrays.asList(ingredient1, ingredient2, ingredient3));
        Stage stage1 = Stage.builder()
                .description("make some with ingr 1 and ingr 2")
                .stageIngredients(
                        Stream.of(
                                StageIngredients.builder().ingredient(ingredient1).valueCount(5).build(),
                                StageIngredients.builder().ingredient(ingredient2).valueCount(7).build()
                        ).collect(Collectors.toUnmodifiableSet())
                )
                .build();
        Stage stage2 = Stage.builder()
                .description("make some with ingr 3")
                .stageIngredients(
                        Stream.of(
                                StageIngredients.builder().ingredient(ingredient3).valueCount(11).build()
                        ).collect(Collectors.toUnmodifiableSet())
                )
                .build();
        Recipe recipe = Recipe.builder()
                .name("best recipes of the world")
                .preparationStages(
                        Stream.of(stage1, stage2).collect(Collectors.toUnmodifiableSet()))
                .build();
        recipeRepository.save(recipe);
        assertThat(recipe).hasFieldOrPropertyWithValue("name", "best recipes of the world");
        assertEquals(recipe.getPreparationStages().size(), 2);
    }
}
