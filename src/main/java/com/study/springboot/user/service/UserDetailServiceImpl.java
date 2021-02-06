package com.study.springboot.user.service;

import com.study.springboot.config.exception.UserNotFoundException;
import com.study.springboot.user.model.UserDetailVO;
import com.study.springboot.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UserNotFoundException {
        return userRepository.findByUserEmail(userEmail).map(u -> new UserDetailVO(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(() -> new UserNotFoundException(userEmail));
    }
}
