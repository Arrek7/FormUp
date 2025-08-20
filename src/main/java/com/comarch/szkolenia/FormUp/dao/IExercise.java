package com.comarch.szkolenia.FormUp.dao;

import com.comarch.szkolenia.FormUp.model.Exercise;

import java.util.List;

public interface IExercise {
    List<Exercise> findAll();
    List<String> findDistinctNames();
}
