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
import org.springframework.transaction.annotation.Transactional;
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
        // Lấy AppUser đang đăng nhập
        AppUser appUser = appUserService.getCurrentUser().orElse(null);

        if (appUser != null) {
            // Tạo Merchant mới
            Merchant merchant = new Merchant();
            merchant.setName(merchantForm.getName());
            merchant.setPhone(merchantForm.getPhone());
            merchant.setEmail(merchantForm.getEmail());
            merchant.setAddress(merchantForm.getAddress());
            merchant.setSlogan(merchantForm.getSlogan());

            // Lưu Merchant mới vào cơ sở dữ liệu
            merchantService.save(merchant);
            System.out.println("Merchant ID after saving: " + merchant.getId());

            // Gán Merchant cho AppUser đang đăng nhập
            appUser.setMerchant(merchant);

            // Lưu lại thông tin AppUser đã gán Merchant
            appUserService.save(appUser);

            // Gửi email thông báo
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

            // Trả về trang home của merchant sau khi đã đăng ký
            ModelAndView mav = new ModelAndView("/merchant/home");
            mav.addObject("merchant", merchant);
            return mav;
        } else {
            // Nếu không tìm thấy AppUser đang đăng nhập
            ModelAndView mav = new ModelAndView("error_page");
            mav.addObject("message", "Không tìm thấy người dùng đăng nhập.");
            return mav;
        }
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






