package com.comarch.szkolenia.FormUp.services.impl;

import com.comarch.szkolenia.FormUp.dao.IExercise;
import com.comarch.szkolenia.FormUp.model.Exercise;
import com.comarch.szkolenia.FormUp.services.IExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService implements IExerciseService {
    private final IExercise exerciseDAO;

    @Override
    public List<String> getExerciseNames() {
        List<String> names = exerciseDAO.findDistinctNames();
        if (names == null || names.isEmpty()) {
            return java.util.List.of();
        }
        return names;
    }

    @Override
    public List<Exercise> getAll() {
        return exerciseDAO.findAll();
    }
}

