package com.wordscreators.easyCook.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ingredient", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private ValueType valueCount;
    private int valueSize;
    private float calories;
    private float sugar;
    private float fat;
    private float salt;

    private boolean isDeleted = false;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<StageIngredients> stageIngredients;
}
