package com.comarch.szkolenia.FormUp.services.impl;

import com.comarch.szkolenia.FormUp.dao.ITreningDAO;
import com.comarch.szkolenia.FormUp.dao.IUserDAO;
import com.comarch.szkolenia.FormUp.model.Trening;
import com.comarch.szkolenia.FormUp.model.User;
import com.comarch.szkolenia.FormUp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDAO userDAO;
    private final ITreningDAO treningDAO;

    @Override
    public Optional<User> getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userDAO.getByLogin(login);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void changePassword(int userId, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return;
        }
        Optional<User> userOpt = userDAO.getById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            userDAO.merge(user);
        }
    }

    @Override
    public Optional<User> updateProfile(int userId,
                                        String name,
                                        String surname,
                                        String email,
                                        Integer age,
                                        Double height,
                                        Double weight) {
        Optional<User> userOpt = userDAO.getById(userId);
        if (userOpt.isEmpty()) return Optional.empty();
        User user = userOpt.get();
        if (name != null) user.setName(name.trim());
        if (surname != null) user.setSurname(surname.trim());
        if (email != null) user.setEmail(email.trim());
        if (age != null) user.setAge(age);
        if (height != null) user.setHeight(height);
        if (weight != null) user.setWeight(weight);
        return userDAO.merge(user);
    }

    @Override
    public Double calculateBMI(User user) {
        if (user == null || user.getHeight() <= 0 || user.getWeight() <= 0) {
            return null;
        }
        double heightInMeters = user.getHeight() / 100.0;
        return user.getWeight() / (heightInMeters * heightInMeters);
    }

    @Override
    public String getBMICategory(Double bmi) {
        if (bmi == null) {
            return null;
        }
        if (bmi < 18.5) {
            return "Niedowaga";
        } else if (bmi < 25) {
            return "Waga prawidłowa";
        } else if (bmi < 30) {
            return "Nadwaga";
        } else {
            return "Otyłość";
        }
    }

    @Override
    public java.util.List<Trening> getTrainingsForUser(User user) {
        if (user == null) return java.util.Collections.emptyList();
        return treningDAO.findByUserId(user.getId());
    }
}
