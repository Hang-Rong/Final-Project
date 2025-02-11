package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.service.impl.EmailService;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;

import java.util.Optional;


@Controller
public class ForgotPasswordController {

    @Autowired
    private IAppUserService userService;

    @Autowired
    private EmailService emailService; // Dịch vụ gửi email

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("username") String username,
                                        @RequestParam("email") String email,
                                        RedirectAttributes redirectAttributes) {
        // Tạo mật khẩu mới
        String newPassword = generateRandomPassword();

        // Giả lập lưu mật khẩu mới vào DB (giả sử username là duy nhất)
        Optional<AppUser> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();
            user.setPassword(newPassword); // Không mã hóa mật khẩu theo yêu cầu
            userService.save(user);

            // Gửi email mật khẩu mới
            emailService.sendEmail(email, "Your New Password", "Your new password is: " + newPassword);

            // Chuyển hướng về trang login với thông báo
            redirectAttributes.addFlashAttribute("message", "A new password has been sent to your email.");
            return "redirect:/login";
        }

        // Nếu không tìm thấy user, quay lại với thông báo lỗi
        redirectAttributes.addFlashAttribute("error", "Username not found.");
        return "redirect:/forgot-password";
    }

    private String generateRandomPassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int PASSWORD_LENGTH = 8;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
