package com.comarch.szkolenia.FormUp.validators;

import com.comarch.szkolenia.FormUp.exceptions.UserValidationException;
import com.comarch.szkolenia.FormUp.model.User;

public class UserValidator {
    public static void validateLogin(String login) {
        String regex = "^\\w{4,}$";
        if(login == null || !login.matches(regex)) {
            throw new UserValidationException("Login must be at least 4 characters long and contain only letters, digits, and underscores");
        }
    }

    public static void validatePassword(String password) {
        String regex = "^\\w{5,}$";

        if(password == null || !password.matches(regex)) {
            throw new UserValidationException("Password must be at least 5 characters long and contain only letters, digits, and underscores");
        }
    }

    public static void validateName(String name) {
        String regex = "^[A-Z]{1}[a-z]{2,}$";
        if(name == null || !name.matches(regex)) {
            throw new UserValidationException("Name must start with an uppercase letter and be at least 3 characters long");
        }
    }

    public static void validateSurname(String surname) {
        String regex = "^[A-Z]{1}[a-z]+([ -]{1}[A-Z]{1}[a-z]+)?$";
        if(surname == null || !surname.matches(regex)) {
            throw new UserValidationException("Surname must start with an uppercase letter and can contain a hyphen or space followed by another uppercase letter");
        }
    }

    public static void checkPasswordsMatch(String password, String confirmPassword) {
        if(password == null || !password.equals(confirmPassword)) {
            throw new UserValidationException("Passwords do not match");
        }
    }

    public static void validateUser(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateName(user.getName());
        validateSurname(user.getSurname());
    }
}
