package com.wordscreators.easyCook.recipe.assembler;

import com.wordscreators.easyCook.recipe.controller.IngredientController;
import com.wordscreators.easyCook.recipe.model.Ingredient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IngredientModelAssembler implements RepresentationModelAssembler<Ingredient, EntityModel<Ingredient>> {
    @Override
    public EntityModel<Ingredient> toModel(Ingredient entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(IngredientController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(IngredientController.class).all()).withRel("ingredients"));
    }
}
