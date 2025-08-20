package com.comarch.szkolenia.FormUp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "texercise_set")
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double weight;
    private Integer repetition;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
}
