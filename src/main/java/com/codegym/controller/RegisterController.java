package com.codegym.controller;

import com.codegym.model.AppRole;
import com.codegym.model.AppUser;
import com.codegym.model.ROLENAME;
import com.codegym.service.IAppRoleService;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAppRoleService appRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ModelAttribute
    public Set<AppRole> getAppUser() {
        AppRole appRole = appRoleService.findByName(ROLENAME.ROLE_USER.toString());
        Set<AppRole> appRoleSet = new HashSet<>();
        appRoleSet.add(appRole);
        return appRoleSet;
    }


    @GetMapping
    public String register(Model model){
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping
    public String register(AppUser user){
//        tao role mac dinh khi dang ky la user
        user.setRoll(getAppUser());
        //String newp = passwordEncoder.encode(user.getPassword());
        //user.setPassword(newp);
//        luu vao trong db
        appUserService.save(user);
        return "redirect:/login";
    }
}