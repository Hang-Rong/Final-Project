package com.codegym.controller;
import com.codegym.model.AppUser;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IAppUserService appUserService;



    @GetMapping()
    public String userList(Model model) {
        Iterable<AppUser> appUsers = appUserService.findAll();
        model.addAttribute("list", appUsers);
        return "user";  // Trả về trang user.html
    }


}
