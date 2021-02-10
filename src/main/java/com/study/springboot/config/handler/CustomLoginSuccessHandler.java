package com.study.springboot.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /*
        AuthenticationProvider를 통해 인증이 성공될 경우 ㅊ처리 로직. /about로 리다이렉트
        나중에 사용자 정보를 꺼내 쓰기위해 Authentication 객체를 SecurityContextHolder Context에 저장한다.
        꺼내쓸때도 해당 Context에서 꺼내쓰면 됨.  (세션방식)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("/index");
    }
}
