package com.comarch.szkolenia.FormUp.dao.impl;

import com.comarch.szkolenia.FormUp.dao.IExercise;
import com.comarch.szkolenia.FormUp.model.Exercise;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExerciseDAO implements IExercise {
    private final SessionFactory sessionFactory;

    @Override
    public List<Exercise> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT e FROM texercise e", Exercise.class)
                    .getResultList();
        }
    }

    @Override
    public List<String> findDistinctNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT DISTINCT e.name FROM texercise e ORDER BY e.name", String.class)
                    .getResultList();
        }
    }
}
