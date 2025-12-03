package com.knu.tubetalk.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(YoutubeApiException.class)
    public String handleYoutubeApiException(YoutubeApiException e, RedirectAttributes redirectAttributes) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/main";
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(SQLException e, RedirectAttributes redirectAttributes) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "데이터베이스 오류가 발생했습니다.");
        return "redirect:/main";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "오류가 발생했습니다: " + e.getMessage());
        return "redirect:/main";
    }
}

