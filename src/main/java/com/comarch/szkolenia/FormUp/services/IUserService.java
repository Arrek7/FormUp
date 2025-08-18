package com.comarch.szkolenia.FormUp.services;

import com.comarch.szkolenia.FormUp.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getById(int id);
    Optional<User> getByLogin(String login);
    List<User> getAll();
    void changePassword(int userId, String newPassword);
}
