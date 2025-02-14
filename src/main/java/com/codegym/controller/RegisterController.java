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
import org.springframework.web.bind.annotation.*;

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


    @ModelAttribute("roles")
    public Set<AppRole> getRoles() {
        AppRole appRole = appRoleService.findByName("ROLE_USER");
        Set<AppRole> appRoleSet = new HashSet<>();
        appRoleSet.add(appRole);
        return appRoleSet;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute("user") AppUser user,
                           @RequestParam("name") String name,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email,
                           @RequestParam("address") String address) {

        // ✅ Mã hóa mật khẩu trước khi lưu
        //String encodedPassword = passwordEncoder.encode(user.getPassword());
        //user.setPassword(encodedPassword);

        // ✅ Tạo Customer và lưu vào DB
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customerService.save(customer);

        // ✅ Gán customer vào AppUser
        user.setCustomer(customer);

        // ✅ Gán quyền ROLE_USER cho AppUser
        user.setRoll(getRoles());

        // ✅ Set mặc định level_of_authority là "USER" dùng ENUM
        user.setLevelOfAuthority(ROLENAME.valueOf(ROLENAME.ROLE_USER.name()));

        // ✅ Lưu vào database
        appUserService.save(user);

        System.out.println("Registered Successfully");

        return "redirect:/login";
    }
}