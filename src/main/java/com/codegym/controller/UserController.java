package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/user/profile");
        Optional<User> cus = userService.findById(id);
        mav.addObject("user", cus);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("/user/create");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView saveUser(@ModelAttribute("user") User user,
                                     @RequestParam("image") MultipartFile image) {
        ModelAndView mav = new ModelAndView("redirect:"+"/user/create");
        String fileName = image.getOriginalFilename();
//        Byte[] files = image.getBytes();
        user.setAvatarImage(fileName);
        userService.save(user);
        mav.addObject("user", user);
        return mav;
    }
}
