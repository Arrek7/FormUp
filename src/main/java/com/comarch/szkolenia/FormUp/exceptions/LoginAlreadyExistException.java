package com.comarch.szkolenia.FormUp.exceptions;

public class LoginAlreadyExistException extends RuntimeException {
    public LoginAlreadyExistException(String message) {
        super(message);
    }
}
