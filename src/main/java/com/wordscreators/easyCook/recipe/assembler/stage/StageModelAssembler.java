package com.wordscreators.easyCook.recipe.assembler.stage;

import com.wordscreators.easyCook.recipe.controller.StageController;
import com.wordscreators.easyCook.recipe.model.Stage;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StageModelAssembler implements RepresentationModelAssembler<Stage, EntityModel<Stage>> {
    @Override
    public EntityModel<Stage> toModel(Stage entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(StageController.class).one(entity.getId())).withSelfRel());
    }
}
