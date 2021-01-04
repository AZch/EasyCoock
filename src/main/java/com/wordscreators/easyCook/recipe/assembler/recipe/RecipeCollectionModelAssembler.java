package com.wordscreators.easyCook.recipe.assembler.recipe;

import com.wordscreators.easyCook.recipe.controller.RecipeController;
import com.wordscreators.easyCook.recipe.model.Recipe;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecipeCollectionModelAssembler implements RepresentationModelAssembler<List<EntityModel<Recipe>>, CollectionModel<EntityModel<Recipe>>> {
    @Override
    public CollectionModel<EntityModel<Recipe>> toModel(List<EntityModel<Recipe>> entity) {
        return CollectionModel.of(entity, linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }
}
