package com.wordscreators.easyCook.recipe.assembler;

import com.wordscreators.easyCook.recipe.controller.IngredientController;
import com.wordscreators.easyCook.recipe.model.Ingredient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class IngredientCollectionModelAssembler implements RepresentationModelAssembler<List<EntityModel<Ingredient>>, CollectionModel<EntityModel<Ingredient>>> {
    @Override
    public CollectionModel<EntityModel<Ingredient>> toModel(List<EntityModel<Ingredient>> entities) {
        return CollectionModel.of(entities, linkTo(methodOn(IngredientController.class).all()).withSelfRel());
    }
}
