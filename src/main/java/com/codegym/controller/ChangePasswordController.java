package com.codegym.controller;


import com.codegym.model.AppUser;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class ChangePasswordController {

    @Autowired
    private IAppUserService userService;

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String processChangePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes,
            Principal principal) {

        // Lấy thông tin user hiện tại
        AppUser user = userService.findByUsername(principal.getName()).orElse(null);

        // Kiểm tra user tồn tại
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/change-password";
        }

        // Kiểm tra mật khẩu hiện tại
        if (!user.getPassword().equals(currentPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Current password is incorrect.");
            return "redirect:/change-password";
        }

        // Kiểm tra xác nhận mật khẩu mới
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New passwords do not match.");
            return "redirect:/change-password";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(newPassword); // Không mã hóa mật khẩu
        userService.save(user);

        redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
        return "redirect:/change-password";
    }
}
