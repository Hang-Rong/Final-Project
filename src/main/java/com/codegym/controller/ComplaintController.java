package com.codegym.controller;

import com.codegym.model.ComplaintForm;
import com.codegym.service.impl.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ComplaintController {

    @Autowired
    private EmailService emailService;

    // Hiển thị form khiếu nại
    @GetMapping("/complaint")
    public String showComplaintForm(Model model) {
        model.addAttribute("complaintForm", new ComplaintForm());
        return "complaint_form";
    }

    // Xử lý form khiếu nại
    @PostMapping("/submitComplaint")
    public String submitComplaint(@ModelAttribute ComplaintForm complaintForm, Model model) {
        try {
            emailService.sendComplaintEmail(complaintForm);
            model.addAttribute("message", "Khiếu nại của bạn đã được gửi thành công!");
        } catch (MessagingException e) {
            model.addAttribute("message", "Lỗi gửi email: " + e.getMessage());
        }
        return "complaint_form";
    }
}
