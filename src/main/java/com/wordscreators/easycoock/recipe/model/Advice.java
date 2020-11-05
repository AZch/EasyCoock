package com.wordscreators.easycoock.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Advice {
    private int id;
    private String content;
}
