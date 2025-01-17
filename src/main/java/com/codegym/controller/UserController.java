package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IAppUserService appUserService;
    @GetMapping()
    public ModelAndView index(){
        Iterable<AppUser> appUsers = appUserService.findAll();
        ModelAndView m = new ModelAndView("/index");
        m.addObject("list", appUsers);
        return m;
    }
}