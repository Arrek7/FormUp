package com.comarch.szkolenia.FormUp.services.impl;

import com.comarch.szkolenia.FormUp.dao.IUserDAO;
import com.comarch.szkolenia.FormUp.model.User;
import com.comarch.szkolenia.FormUp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDAO userDAO;

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
}
