package com.codegym.controller;

import com.codegym.model.AppRole;
import com.codegym.model.AppUser;
import com.codegym.model.Customer;
import com.codegym.model.ROLENAME;
import com.codegym.service.IAppRoleService;
import com.codegym.service.IAppUserService;
import com.codegym.service.ICustomerService;
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
    private ICustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ModelAttribute
    public Set<AppRole> getAppUser() {
        AppRole appRole = appRoleService.findByName("ROLE_USER");
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
    public String register(AppUser user, String name, String phone, String email, String address, String avatarImage){

//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customerService.save(customer);

        user.setCustomer(customer);

        user.setRoll(getAppUser());
        appUserService.save(user);

        return "redirect:/login"; 
    }
}