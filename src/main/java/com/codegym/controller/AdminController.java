package com.codegym.controller;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin")
public class AdminController {




    @Autowired
    private IAppUserService appUserService;

    @GetMapping()
    public String getAllUser(){
        return "/admin";
    }



}