package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.model.Merchant;
import com.codegym.service.impl.AppUserService;
import com.codegym.service.impl.EmailService;
import com.codegym.service.impl.MerchantService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/home")
    public String merchant(Model model, @RequestParam("id") Long id) {
        Optional<Merchant> merchantOptional = merchantService.findById(id);
        if (merchantOptional.isPresent()) {
            model.addAttribute("merchant", merchantOptional.get());
            return "merchant/home";
        } else {
            return "/merchant";
        }
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView("/merchant/register");
        mav.addObject("merchantForm", new Merchant());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerSubmit(@ModelAttribute("merchantForm") Merchant merchantForm) {
        // Tạo Merchant từ form dữ liệu
        Merchant merchant = new Merchant();
        merchant.setName(merchantForm.getName());
        merchant.setPhone(merchantForm.getPhone());
        merchant.setEmail(merchantForm.getEmail());
        merchant.setAddress(merchantForm.getAddress());
        merchant.setSlogan(merchantForm.getSlogan());

        // Lưu Merchant vào cơ sở dữ liệu
        merchantService.save(merchant);

        // Tạo AppUser mới và liên kết với Merchant
        AppUser appUser = new AppUser();
        appUser.setUsername(merchantForm.getEmail()); // Sử dụng email làm username
        appUser.setPassword("defaultPassword"); // Cần có mật khẩu mặc định hoặc thêm logic để tạo mật khẩu
        appUser.setMerchant(merchant); // Liên kết AppUser với Merchant

        // Lưu AppUser vào cơ sở dữ liệu
        appUserService.save(appUser);

//        // Tạo nội dung email
//        String subject = "New Merchant Registration";
//        String content = "<h3>Thông tin đăng ký merchant:</h3>"
//                + "<p><b>Name:</b> " + merchant.getName() + "</p>"
//                + "<p><b>Phone:</b> " + merchant.getPhone() + "</p>"
//                + "<p><b>Email:</b> " + merchant.getEmail() + "</p>"
//                + "<p><b>Address:</b> " + merchant.getAddress() + "</p>"
//                + "<p><b>Slogan:</b> " + merchant.getSlogan() + "</p>";
//
//        try {
//            emailService.sendRegistrationEmail("hangrongv25@gmail.com", subject, content);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        // Trả về trang home sau khi đăng ký thành công
        ModelAndView mav = new ModelAndView("/merchant/home");
        mav.addObject("merchant", merchant);
        return mav;
    }

}
