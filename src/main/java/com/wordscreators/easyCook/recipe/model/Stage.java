package com.wordscreators.easyCook.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Stage {
    private Long id;
    private int capacity;
    private ValueType valueType;
    private float valueCount;
}
