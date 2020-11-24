package com.wordscreators.easyCook;


import com.wordscreators.easyCook.recipe.assembler.ingredient.IngredientCollectionModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.ingredient.IngredientModelAssembler;
import com.wordscreators.easyCook.recipe.controller.IngredientController;
import com.wordscreators.easyCook.recipe.model.Ingredient;
import com.wordscreators.easyCook.recipe.model.ValueType;
import com.wordscreators.easyCook.recipe.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
@Import({ IngredientModelAssembler.class, IngredientCollectionModelAssembler.class })
public class IngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Test
    public void getAllIngredientsShouldUseFindIngredientsByIsDeleted() throws Exception {
        Ingredient ingredient1 = Ingredient.builder()
                .name("test ingredient")
                .valueCount(ValueType.GRAM)
                .valueSize(100)
                .calories(50)
                .sugar(51)
                .fat(52)
                .salt(53)
                .description("best ingredient of the best")
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
        System.out.println(ingredientRepository);
        when(ingredientRepository.findIngredientsByIsDeleted(false)).thenReturn(Arrays.asList(ingredient1, ingredient2));

        this.mockMvc
                .perform(get("/ingredient"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ingredients[*]", hasSize(2)));
    }
}
