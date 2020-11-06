package com.wordscreators.easyCook.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Ingredients {
    private Long id;
    private String name;

    private ValueType valueCount;
    private int valueSize;
    private float calories;
    private float sugar;
    private float fat;
    private float salt;

    private String description;
    private List<Advice> advices;
}
