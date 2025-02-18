package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.service.IAppUserService;

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




    @GetMapping()
    public String userList(Model model) {
        Iterable<AppUser> appUsers = appUserService.findAll();
        model.addAttribute("list", appUsers);
        return "user";
    }


//    @GetMapping("/request-form")
//    public String showRequestForm(Model model) {
//        model.addAttribute("requestRegister", new RequestRegister());
//        return "request-form";
//    }
//
//    @PostMapping("/send-request")
//    public String sendRequest(@ModelAttribute RequestRegister requestRegister, @RequestParam Long userId) {
//        AppUser appUser = appUserService.findById(userId).orElse(null);
//        if (appUser != null) {
//            requestRegister.setCustomer(appUser.getCustomer());
//            requestRegister.setDate(new Date());
//            requestRegister.setSent(true);
//
//            requestRegisterService.save(requestRegister);
//            return "redirect:/user/request-form";
//        }else {
//            return "error";
//        }
//    }


}
