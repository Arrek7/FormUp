package com.comarch.szkolenia.FormUp.dao;

import com.comarch.szkolenia.FormUp.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> merge(User user);
    Optional<User> getById(int id);
    Optional<User> getByLogin(String login);
    Optional<User> getByEmail(String email);
    List<User> getAll();
}
