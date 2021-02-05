package com.study.springboot.user.service;

import com.study.springboot.user.model.UserVO;

public interface UserService {

    UserVO login(UserVO userVO);

    UserVO createUser(UserVO userVO);

    UserVO findUserByUserEmail(String userEmail);
}
