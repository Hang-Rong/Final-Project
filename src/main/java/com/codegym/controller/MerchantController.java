package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.model.Customer;
import com.codegym.model.Merchant;
import com.codegym.model.Product;
import com.codegym.service.impl.AppUserService;
import com.codegym.service.impl.EmailService;
import com.codegym.service.impl.MerchantService;
import com.codegym.service.impl.ProductService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private ProductService productService;


    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView("/merchant/register");
        mav.addObject("merchantForm", new Merchant());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerSubmit(@ModelAttribute("merchantForm") Merchant merchantForm) {

        Merchant merchant = new Merchant();
        merchant.setName(merchantForm.getName());
        merchant.setPhone(merchantForm.getPhone());
        merchant.setEmail(merchantForm.getEmail());
        merchant.setAddress(merchantForm.getAddress());
        merchant.setSlogan(merchantForm.getSlogan());

        merchantService.save(merchant);


        AppUser appUser = new AppUser();
        appUser.setUsername(merchantForm.getEmail());
        appUser.setPassword("defaultPassword");
        appUser.setMerchant(merchant);

        appUserService.save(appUser);

        String subject = "New Merchant Registration";
        String content = "<h3>Thông tin đăng ký merchant:</h3>"
                + "<p><b>Name:</b> " + merchant.getName() + "</p>"
                + "<p><b>Phone:</b> " + merchant.getPhone() + "</p>"
                + "<p><b>Email:</b> " + merchant.getEmail() + "</p>"
                + "<p><b>Address:</b> " + merchant.getAddress() + "</p>"
                + "<p><b>Slogan:</b> " + merchant.getSlogan() + "</p>";

        try {
            emailService.sendRegistrationEmail("hangrongv25@gmail.com", subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        ModelAndView mav = new ModelAndView("/merchant/home");
        mav.addObject("merchant", merchant);
        return mav;
    }


    @GetMapping("/customer-info")
    public String getCustomerInfo(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username).orElse(null);

        if (appUser != null) {
            Customer customer = appUser.getCustomer();
            if (customer != null) {
                model.addAttribute("customer", customer);
                return "customer_info";
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






