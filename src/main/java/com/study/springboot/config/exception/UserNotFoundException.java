package com.study.springboot.config.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userEmail) {
        super(userEmail + " NotFoundException");
    }
}
