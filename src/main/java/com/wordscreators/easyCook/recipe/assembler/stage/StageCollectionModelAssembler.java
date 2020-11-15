package com.wordscreators.easyCook.recipe.assembler.stage;

import com.wordscreators.easyCook.recipe.model.Stage;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StageCollectionModelAssembler implements RepresentationModelAssembler<List<EntityModel<Stage>>, CollectionModel<EntityModel<Stage>>> {
    @Override
    public CollectionModel<EntityModel<Stage>> toModel(List<EntityModel<Stage>> entity) {
        return CollectionModel.of(entity);
    }
}
