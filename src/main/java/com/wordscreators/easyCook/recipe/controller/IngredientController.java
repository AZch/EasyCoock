package com.wordscreators.easyCook.recipe.controller;

import com.wordscreators.easyCook.recipe.assembler.ingredient.IngredientCollectionModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.ingredient.IngredientModelAssembler;
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
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    private final IngredientModelAssembler assembler;
    private final IngredientCollectionModelAssembler collectionAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Ingredient>> all() {
        List<EntityModel<Ingredient>> ingredients = ingredientRepository.findIngredientsByIsDeleted(false)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return collectionAssembler.toModel(ingredients);
    }

    @GetMapping("/{id}")
    public EntityModel<Ingredient> one(@PathVariable Long id) {
        Ingredient ingredient = ingredientRepository.findIngredientByIdAndIsDeleted(id, false)
                .orElseThrow(() -> ingredientNotFoundById(id));
        return assembler.toModel(ingredient);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Ingredient>> newIngredient(@RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientRepository.save(ingredient);

        return ResponseEntity.created(
                linkTo(
                        methodOn(IngredientController.class).one(newIngredient.getId())).toUri())
                .body(assembler.toModel(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ingredient>> replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable Long id) {
        Ingredient updatedIngredient = ingredientRepository.findIngredientByIdAndIsDeleted(id, false).map(ingredient -> {
            ingredient.setName(newIngredient.getName());
            ingredient.setCalories(newIngredient.getCalories());
            ingredient.setFat(newIngredient.getFat());
            ingredient.setDescription(newIngredient.getDescription());
            ingredient.setSalt(newIngredient.getSalt());
            ingredient.setSugar(newIngredient.getSugar());
            ingredient.setValueCount(newIngredient.getValueCount());
            ingredient.setValueSize(newIngredient.getValueSize());
            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> ingredientNotFoundById(id));

        EntityModel<Ingredient> entityModel = assembler.toModel(updatedIngredient);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        ingredientRepository.findIngredientByIdAndIsDeleted(id, false)
                .map(ingredient -> {
                    ingredient.setDeleted(true);
                    return ingredientRepository.save(ingredient);
                })
                .orElseThrow(() -> ingredientNotFoundById(id));

        return ResponseEntity.noContent().build();
    }

    public static RuntimeException ingredientNotFoundById(Long id) {
        return new EntityNotFoundException("Ingredient", "id", id);
    }
}
