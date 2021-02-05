package com.study.springboot.config.security;

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
                // about 요청시 로그인 요구
                .antMatchers("/about").authenticated()
                // admin 요청시 ROLE_ADMIN 역활을 가지고 있어야 함
                .antMatchers("/admin").hasRole("ADMIN")
                // 나머지 요청은 로그인 필요X
                .anyRequest().permitAll()
                .and()
                // 로그인하는 경우 설정
                .formLogin()
                // 로그인 페이지 URL
                .loginPage("/user/loginView")
                // 로그인 성공 URL
                .successForwardUrl("/index")
                // 로그인 실패 URL
                .failureForwardUrl("/index")
                .permitAll()
                .and()
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
        customAuthenticationFilter.setFilterProcessesUrl("/user/login"); // 로그인 요청 URL
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler()); // 로그인 성공 핸들러
        customAuthenticationFilter.afterPropertiesSet(); // 생성된 Bean을 주입하기 위한 초기화
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
}
