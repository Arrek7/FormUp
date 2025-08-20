package com.comarch.szkolenia.FormUp.dao.impl;

import com.comarch.szkolenia.FormUp.dao.ITreningDAO;
import com.comarch.szkolenia.FormUp.model.Trening;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TreningDAO implements ITreningDAO {
    private final SessionFactory sessionFactory;

    @Override
    public List<Trening> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT t FROM ttrening t WHERE t.user.id = :userId", Trening.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    @Override
    public Optional<Trening> save(Trening trening) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Trening merged = (Trening) session.merge(trening);
            session.getTransaction().commit();
            return Optional.ofNullable(merged);
        } catch (Exception e) {
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }
}
