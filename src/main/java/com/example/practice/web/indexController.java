package com.example.practice.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class indexController {
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String LoginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // セッションを無効にする
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // ログアウト処理が完了したらトップページにリダイレクトする
        return "redirect:/";
    }
}