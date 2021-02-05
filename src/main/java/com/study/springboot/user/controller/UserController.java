package com.study.springboot.user.controller;

import com.study.springboot.config.role.UserRole;
import com.study.springboot.user.model.UserVO;
import com.study.springboot.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor // 생성자 자동 주입 ( Autowired )
@Controller
@RequestMapping("/user") // user로 들어오는 URL을 처리함
@Log4j2
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @NonNull // BCryptPasswordEncoder가 NULL로 넘어오면 Exception 발생시킴.
    private BCryptPasswordEncoder passwordEncoder; // 비밀번호 해싱용

    @GetMapping("/loginView")
    public String loginView() {
        return "user/loginView";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute UserVO userVO){
        log.error("@@@");
        String userPw = userVO.getUserPw();
        userVO = userService.findUserByUserEmail(userVO.getUserEmail());
        if(userVO == null || !passwordEncoder.matches(userPw, userVO.getUserPw())){
            redirectAttributes.addFlashAttribute("rsMsg", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "user/loginView";
        }
        request.getSession().setAttribute("userVO", userVO); // 로그인 성공시 세션에 유저 정보 삽입
        return "index";
    }

    @GetMapping(value = "/init")
    public String createAdmin(@ModelAttribute UserVO userVO){
        userVO.setUserEmail("user@naver.com");
        userVO.setUserPw(passwordEncoder.encode("test"));
        userVO.setRole(UserRole.USER); // UserRole은 ENUM으로 정의중. 추가하면 role을 수정 할 것.
        if(userService.createUser(userVO) == null){
            log.error("Create Admin Error");
        }

        userVO.setUserEmail("test@naver.com");
        userVO.setUserPw(passwordEncoder.encode("test"));
        userVO.setRole(UserRole.ADMIN);
        if(userService.createUser(userVO) == null){
            log.error("Create Admin Error");
        }
        //return "redirect:/index";
        return "user/loginView";
    }
}
