package com.comarch.szkolenia.FormUp.services;

import com.comarch.szkolenia.FormUp.model.Exercise;

import java.util.List;

public interface IExerciseService {
    List<String> getExerciseNames();
    List<Exercise> getAll();
}

