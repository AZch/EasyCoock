package com.wordscreators.easycoock.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Stage {
    private int id;
    private int capacity;
    private ValueType valueType;
    private float valueCount;
}
