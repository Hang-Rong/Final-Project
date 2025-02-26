package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.model.Customer;
import com.codegym.service.IAppUserService;

import com.codegym.service.impl.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private CurrentUserService currentUserService;




    @GetMapping()
    public String userList(Model model) {
        Iterable<AppUser> appUsers = appUserService.findAll();
        model.addAttribute("list", appUsers);
        return "user";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        AppUser appUser = currentUserService.getCurrentUser();

        if (appUser != null) {
            Customer customer = appUser.getCustomer();
            if (customer != null) {
                model.addAttribute("appUser", appUser);
                model.addAttribute("customer", customer);
                return "user/profile";
            } else {
                model.addAttribute("message", "Không tìm thấy thông tin khách hàng.");
                return "error_page";
            }
        } else {
            model.addAttribute("message", "Không tìm thấy người dùng.");
            return "error_page";
        }
    }



}
