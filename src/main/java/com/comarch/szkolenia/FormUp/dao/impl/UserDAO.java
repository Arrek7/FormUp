package com.comarch.szkolenia.FormUp.dao.impl;

import com.comarch.szkolenia.FormUp.dao.IUserDAO;
import com.comarch.szkolenia.FormUp.model.User;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAO implements IUserDAO {
    private final SessionFactory sessionFactory;

    private final String GET_BY_ID_HQL = "FROM com.comarch.szkolenia.FormUp.model.User WHERE id = :id";
    private final String GET_BY_LOGIN_HQL = "FROM com.comarch.szkolenia.FormUp.model.User WHERE login = :login";
    private final String GET_BY_EMAIL_HQL = "FROM com.comarch.szkolenia.FormUp.model.User WHERE email = :email";
    private final String GET_ALL_HQL = "FROM com.comarch.szkolenia.FormUp.model.User";

    @Override
    public Optional<User> merge(User user) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            User merged = (User) session.merge(user);
            session.getTransaction().commit();
            return Optional.ofNullable(merged);
        } catch (Exception e) {
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_ID_HQL, User.class);
        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_LOGIN_HQL, User.class);
        query.setParameter("login", login);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_EMAIL_HQL, User.class);
        query.setParameter("email", email);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_ALL_HQL, User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }
}
