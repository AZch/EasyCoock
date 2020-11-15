package com.wordscreators.easyCook.recipe.controller;

import com.wordscreators.easyCook.recipe.assembler.IngredientCollectionModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.IngredientModelAssembler;
import com.wordscreators.easyCook.recipe.exception.EntityNotFoundException;
import com.wordscreators.easyCook.recipe.model.Ingredient;
import com.wordscreators.easyCook.recipe.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    private final IngredientModelAssembler ingredientModelAssembler;
    private final IngredientCollectionModelAssembler ingredientCollectionModelAssembler;

    @GetMapping("/")
    public CollectionModel<EntityModel<Ingredient>> all() {
        List<EntityModel<Ingredient>> ingredients = ingredientRepository.findAll()
                .stream()
                .map(ingredientModelAssembler::toModel)
                .collect(Collectors.toList());

        return ingredientCollectionModelAssembler.toModel(ingredients);
    }

    @GetMapping("/{id}")
    public EntityModel<Ingredient> one(@PathVariable Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient", "id", id));
        return ingredientModelAssembler.toModel(ingredient);
    }

    @PostMapping("/")
    public ResponseEntity<EntityModel<Ingredient>> newIngredient(@RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientRepository.save(ingredient);

        return ResponseEntity.created(
                linkTo(
                        methodOn(IngredientController.class).one(newIngredient.getId()))
                        .toUri())
                .body(ingredientModelAssembler.toModel(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ingredient>> replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable Long id) {
        Ingredient updatedIngredient = ingredientRepository.findById(id).map(ingredient -> {
            ingredient.setName(newIngredient.getName());
            ingredient.setCalories(newIngredient.getCalories());
            ingredient.setFat(newIngredient.getFat());
            ingredient.setDescription(newIngredient.getDescription());
            ingredient.setSalt(newIngredient.getSalt());
            ingredient.setSugar(newIngredient.getSugar());
            ingredient.setValueCount(newIngredient.getValueCount());
            ingredient.setValueSize(newIngredient.getValueSize());
            return ingredient;
        }).orElseGet(() -> {
            newIngredient.setId(id);
            return ingredientRepository.save(newIngredient);
        });

        EntityModel<Ingredient> entityModel = ingredientModelAssembler.toModel(updatedIngredient);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        ingredientRepository.findById(id)
                .map(ingredient -> {
                    ingredient.setDeleted(true);
                    return ingredient;
                })
                .orElseThrow(() -> new EntityNotFoundException("Ingredient", "id", id));

        return ResponseEntity.noContent().build();
    }
}
