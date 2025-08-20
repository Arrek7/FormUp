package com.comarch.szkolenia.FormUp.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TrainingForm {
    private String name;
    private List<ExerciseForm> exercises = new ArrayList<>();

    @Getter
    @Setter
    public static class ExerciseForm {
        private String name;
        private List<SetForm> sets = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class SetForm {
        private Double weight;
        private Integer repetition;
    }
}
