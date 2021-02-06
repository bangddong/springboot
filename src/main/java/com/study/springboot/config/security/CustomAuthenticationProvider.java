package com.study.springboot.config.security;

import com.study.springboot.user.model.UserDetailVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;
    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // AuthenticationFilter에서 생성된 토큰으로부터 아이디 및 비밀번호 조회
        String userEmail = token.getName();
        String userPw = (String)token.getCredentials();
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        // DB에서 사용자 조회
        UserDetailVO userDetailVO = (UserDetailVO) userDetailsService.loadUserByUsername(userEmail);
        if (!passwordEncoder.matches(userPw, userDetailVO.getPassword())) {
            throw new BadCredentialsException(userDetailVO.getUsername() + "Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetailVO, userPw, userDetailVO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
