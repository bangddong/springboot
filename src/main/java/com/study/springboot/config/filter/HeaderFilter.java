package com.study.springboot.config.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class HeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*"); // CORS 에러 방지를 위한 모든 주소에서의 요청 허용
        res.setHeader("Access-Control-Allow-Methods", "GET, POST"); // GET 요청과 POST 요청만 허용
        res.setHeader("Access-Control-Max_Age", "3600"); // 캐싱에 소요될 최대 시간
        res.setHeader(
                "Access-Control-Allow-Headers", // 허용 할 헤더 목록
                "X-Requested-With, Content-Type, Authorization, X-XSRF-token" // AJAX 요청, 요청 타입, 인증값, 인증토큰 종류
        );
        res.setHeader("Access-Control-Allow-Credentials", "false"); // 자바스크립트 응답 브라우저 노출 여부

        chain.doFilter(request, response);
    }
}
