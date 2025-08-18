package com.comarch.szkolenia.FormUp.services;

import com.comarch.szkolenia.FormUp.model.User;

public interface IAuthenticationService {
    void register(User user);
    void authenticate(String username, String password);
    void logout();
}
