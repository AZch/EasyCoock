package com.wordscreators.easyCook.recipe.controller;

import com.wordscreators.easyCook.recipe.assembler.recipe.RecipeCollectionModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.recipe.RecipeModelAssembler;
import com.wordscreators.easyCook.recipe.exception.EntityNotFoundException;
import com.wordscreators.easyCook.recipe.model.Recipe;
import com.wordscreators.easyCook.recipe.repository.RecipeRepository;
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
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeRepository recipeRepository;

    private final RecipeCollectionModelAssembler collectionAssembler;
    private final RecipeModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Recipe>> all() {
        List<EntityModel<Recipe>> recipes = recipeRepository.findRecipesByIsDeleted(false)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return collectionAssembler.toModel(recipes);
    }

    @GetMapping("/{id}")
    public EntityModel<Recipe> one(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findRecipeByIdAndIsDeleted(id, false)
                .orElseThrow(() -> recipeNotFoundById(id));

        return assembler.toModel(recipe);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Recipe>> newRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe = recipeRepository.save(recipe);

        return ResponseEntity
                .created(linkTo(methodOn(RecipeController.class).one(newRecipe.getId())).toUri())
                .body(assembler.toModel(newRecipe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Recipe>> replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {
        Recipe updatedRecipe = recipeRepository.findRecipeByIdAndIsDeleted(id, false)
                .map(recipe -> {
                    recipe.setName(newRecipe.getName());
                    return recipeRepository.save(recipe);
                }).orElseThrow(() -> recipeNotFoundById(id));

        EntityModel<Recipe> recipeModel = assembler.toModel(updatedRecipe);

        return ResponseEntity
                .created(recipeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(recipeModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeRepository.findRecipeByIdAndIsDeleted(id, false)
                .map(recipe -> {
                    recipe.setDeleted(true);
                    return recipeRepository.save(recipe);
                }).orElseThrow(() -> recipeNotFoundById(id));

        return ResponseEntity.noContent().build();
    }

    private RuntimeException recipeNotFoundById(Long id) {
        return new EntityNotFoundException("Recipe", "id", id);
    }
}
