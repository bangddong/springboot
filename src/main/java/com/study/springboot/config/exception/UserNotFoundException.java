package com.study.springboot.config.exception;

public class UserNotFoundException extends RuntimeException {

    /**
     * 입력받은 이메일과 매칭되는 데이터가 없음.
     * @param userEmail
     */
    public UserNotFoundException(String userEmail) {
        super(userEmail + " NotFoundException");
    }
}
