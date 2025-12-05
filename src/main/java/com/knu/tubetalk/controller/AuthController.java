package com.knu.tubetalk.controller;

import com.knu.tubetalk.domain.User;
import com.knu.tubetalk.service.UserService;

import java.security.Principal;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {
	
	private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

//    @GetMapping("/main")
//    public String mainPage() {
//        return "main";
//    }

    @GetMapping("/delete")
    public String deletePage() {
        return "delete";
    }
    
    @GetMapping("/userInfoChange")
    public String userInfoChangePage(Model model, Principal principal) throws SQLException {
        // 로그인 안 한 사람이면 로그인 페이지로 쫓아냄
        if (principal == null) {
            return "redirect:/login";
        }

        // 로그인한 사람의 ID를 가져와서 DB에서 정보를 찾음
        String loginId = principal.getName();
        User user = userService.loadUserByLoginId(loginId);

        // HTML에서 쓸 수 있게 "user"라는 이름으로 정보 담기
        model.addAttribute("user", user);

        // templates/userInfoChange.html 파일을 보여줌
        return "userInfoChange";
    }
}
