package com.study.springboot.config.security;

import com.study.springboot.config.handler.CustomLoginFailHandler;
import com.study.springboot.config.handler.CustomLoginSuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // SpringSecurityFilterChain 포함시키는 어노테이션
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 정적자원 통과
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                // 특정 URL에 대한 로그인 요청. 아래 예시는 /about URL에 대한 인증을 검사.
                //.antMatchers("/about").authenticated()
                .antMatchers("/user/register", "/user/init").permitAll()
                // admin 요청시 ROLE_ADMIN 역활을 가지고 있어야 함
                .antMatchers("/admin").hasRole("ADMIN")
                // 나머지 요청은 무조건 로그인 필요
                .anyRequest().authenticated()
                .and()
                // 로그인하는 경우 설정 ( Form 방식의 로그인 )
            .formLogin()
                // 로그인 페이지 URL
                .loginPage("/user/loginView")
                // 로그인 성공 URL
                .successForwardUrl("/index")
                // 로그인 실패 URL
                .failureForwardUrl("/user/loginView")
                .permitAll()
                .and()
                // 로그인시 사용할 필터
            .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // BCrypt 해시 함수 이용 비밀번호 해싱
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/user/login"); // 로그인 요청 URL, 스프링에서 제공하니 별도로 컨트롤러 설정 필요 X
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler()); // 로그인 성공 핸들러
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailHandler()); // 로그인 실패 핸들러도 정의 가능.
        customAuthenticationFilter.afterPropertiesSet(); // 생성된 Bean을 주입하기 위한 초기화
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CustomLoginFailHandler customLoginFailHandler() { return new CustomLoginFailHandler(); }
}
