package com.comarch.szkolenia.FormUp.services.impl;

import com.comarch.szkolenia.FormUp.dao.IUserDAO;
import com.comarch.szkolenia.FormUp.exceptions.LoginAlreadyExistException;
import com.comarch.szkolenia.FormUp.model.User;
import com.comarch.szkolenia.FormUp.services.IAuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IUserDAO userDAO;

    @Autowired
    private HttpSession session;

    @Override
    public void register(User user) {
        if (this.userDAO.getByLogin(user.getLogin()).isPresent()) {
            throw new LoginAlreadyExistException("Login already exists: " + user.getLogin());
        }

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setRole(User.Role.USER);
        this.userDAO.merge(user);
    }

    @Override
    public void authenticate(String login, String password) {
        Optional<User> userBox = this.userDAO.getByLogin(login);
        if (userBox.isPresent() &&
                DigestUtils.md5DigestAsHex(password.getBytes()).equals(userBox.get().getPassword())) {
            session.setAttribute("user", userBox.get());
        }
    }

    @Override
    public void logout() {
        session.removeAttribute("user");
    }
}
