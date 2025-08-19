package com.comarch.szkolenia.FormUp.dao.impl;

import com.comarch.szkolenia.FormUp.dao.ITreningDAO;
import com.comarch.szkolenia.FormUp.model.Trening;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreningDAO implements ITreningDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Trening> findByUserId(int userId) {
        return entityManager.createQuery(
                        "SELECT t FROM ttrening t WHERE t.user.id = :userId", Trening.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
