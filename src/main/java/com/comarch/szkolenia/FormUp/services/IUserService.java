package com.comarch.szkolenia.FormUp.services;

import com.comarch.szkolenia.FormUp.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getById(int id);
    Optional<User> getByLogin(String login);
    List<User> getAll();
    void changePassword(int userId, String newPassword);
    Optional<User> updateProfile(int userId, String name, String surname, String email, Integer age, Double height, Double weight);
    Double calculateBMI(User user);
    String getBMICategory(Double bmi);
    List<com.comarch.szkolenia.FormUp.model.Trening> getTrainingsForUser(com.comarch.szkolenia.FormUp.model.User user);
}
