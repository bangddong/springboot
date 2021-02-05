package com.study.springboot.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class UserDetailVO implements UserDetails {

    @Delegate
    private UserVO userVO;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userVO.getUserPw();
    }

    @Override
    public String getUsername() {
        return userVO.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userVO.getInEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userVO.getInEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userVO.getInEnable();
    }

    @Override
    public boolean isEnabled() {
        return userVO.getInEnable();
    }
}
