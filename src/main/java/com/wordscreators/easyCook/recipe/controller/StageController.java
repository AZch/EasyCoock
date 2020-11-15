package com.wordscreators.easyCook.recipe.controller;

import com.wordscreators.easyCook.recipe.assembler.recipe.RecipeCollectionModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.recipe.RecipeModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.stage.StageCollectionModelAssembler;
import com.wordscreators.easyCook.recipe.assembler.stage.StageModelAssembler;
import com.wordscreators.easyCook.recipe.exception.EntityNotFoundException;
import com.wordscreators.easyCook.recipe.model.Stage;
import com.wordscreators.easyCook.recipe.repository.StageRepository;
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
@RequiredArgsConstructor
@RequestMapping("/stage")
public class StageController {

    private final StageRepository stageRepository;

    private final StageModelAssembler assembler;
    private final StageCollectionModelAssembler collectionAssembler;

    @GetMapping("/byRecipe/{id}")
    public CollectionModel<EntityModel<Stage>> stagesByRecipe(@PathVariable Long id) {
        List<EntityModel<Stage>> stages = stageRepository.findStagesByRecipe_Id(id)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return collectionAssembler.toModel(stages);
    }

    @GetMapping("/{id}")
    public EntityModel<Stage> one(@PathVariable Long id) {
        Stage stage = stageRepository.findStageByIdAndIsDeleted(id, false)
                .orElseThrow(() -> stageNotFoundById(id));

        return assembler.toModel(stage);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Stage>> newStage(@RequestBody Stage stage) {
        Stage newStage = stageRepository.save(stage);

        return ResponseEntity
                .created(linkTo(methodOn(StageController.class).one(newStage.getId())).toUri())
                .body(assembler.toModel(stage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Stage>> replaceStage(@RequestBody Stage newStage, @PathVariable Long id) {
        Stage updatedStage = stageRepository.findStageByIdAndIsDeleted(id, false)
                .map(stage -> {
                    stage.setDescription(newStage.getDescription());
                    return stageRepository.save(stage);
                })
                .orElseThrow(() -> stageNotFoundById(id));

        EntityModel<Stage> stageModel = assembler.toModel(updatedStage);

        return ResponseEntity
                .created(stageModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(stageModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStage(@PathVariable Long id) {
        stageRepository.findStageByIdAndIsDeleted(id, false)
                .map(stage -> {
                    stage.setDeleted(true);
                    return stageRepository.save(stage);
                })
                .orElseThrow(() -> stageNotFoundById(id));

        return ResponseEntity.noContent().build();
    }

    private RuntimeException stageNotFoundById(Long id) {
        return new EntityNotFoundException("Stage", "id", id);
    }
}
