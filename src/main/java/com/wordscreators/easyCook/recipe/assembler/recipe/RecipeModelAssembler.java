package com.wordscreators.easyCook.recipe.assembler.recipe;

import com.wordscreators.easyCook.recipe.controller.RecipeController;
import com.wordscreators.easyCook.recipe.model.Recipe;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecipeModelAssembler implements RepresentationModelAssembler<Recipe, EntityModel<Recipe>> {
    @Override
    public EntityModel<Recipe> toModel(Recipe entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RecipeController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(RecipeController.class).all()).withRel("recipes"));
    }
}
