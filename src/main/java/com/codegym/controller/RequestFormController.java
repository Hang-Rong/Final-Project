package com.codegym.controller;

import com.codegym.model.RequestForm;
import com.codegym.model.AppUser;
import com.codegym.repository.RequestFormRepo;
import com.codegym.service.impl.AppUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class RequestFormController {

    @Autowired
    private RequestFormRepo requestFormRepository;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/request-form")
    public String showRequestForm(Model model) {
        model.addAttribute("requestForm", new RequestForm());
        return "request-form";  // Đảm bảo có view này
    }


    @PostMapping("/submit-request")
    public String submitRequestForm(@ModelAttribute RequestForm requestForm, Model model) {
        // Lấy thông tin người dùng hiện tại
        AppUser currentUser = appUserService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        requestForm.setAppUser(currentUser);
        LocalDate localDate = LocalDate.now(); // Lấy ngày hiện tại
        Date date = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        requestForm.setDate(date);

        requestFormRepository.save(requestForm);

        // Thêm thông báo thành công vào model
        model.addAttribute("message", "Yêu cầu của bạn đã được gửi thành công!");

        return "request-success";
    }
}
