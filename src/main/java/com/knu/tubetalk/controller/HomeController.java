package com.knu.tubetalk.controller;

import com.knu.tubetalk.dto.TrendingThread;
import com.knu.tubetalk.service.ThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class HomeController {
	
	private final ThreadService threadService;

    public HomeController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping({"/", "/main"})
    public String mainPage(Model model) {
    	try {
            // 2. 인기 스레드 가져오기
            List<TrendingThread> trending = threadService.getTrendingThreads();
            model.addAttribute("trending", trending);
        } catch (SQLException e) {
            e.printStackTrace();
            // 에러 나도 메인 페이지는 떠야 하므로 비워둠 (로그만 출력)
        }
        return "main";  // main.html or main.jsp 등
    }
}