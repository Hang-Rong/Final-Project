package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        System.out.println("Received error param: " + error); // Debug xem có nhận error không

        if ("banned".equals(error)) {
            model.addAttribute("errorMessage", "Tài khoản của bạn đã bị BANNED!");
        } else if (error != null) {
            model.addAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu.");
        }

        return "login";
    }
}
