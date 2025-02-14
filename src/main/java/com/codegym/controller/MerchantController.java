package com.codegym.controller;

import com.codegym.model.Merchant;
import com.codegym.model.MerchantForm;
import com.codegym.service.impl.EmailService;
import com.codegym.service.impl.MerchantService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView("/merchant/register");
        mav.addObject("merchantForm", new MerchantForm());
        return mav;
    }

//    @PostMapping("/register")
//    public ModelAndView registerSubmit(@ModelAttribute("merchant") Merchant merchant) {
//        merchantService.save(merchant);
//        ModelAndView mav = new ModelAndView("/merchant/register");
//        mav.addObject("merchant", merchant);
//        return mav;
//    }

    @PostMapping("/register")
    public ModelAndView registerSubmit(@ModelAttribute("merchantForm") MerchantForm merchantForm) {
        Merchant merchant = new Merchant();

        merchant.setName(merchantForm.getName());
        merchant.setPhone(merchantForm.getPhone());
        merchant.setEmail(merchantForm.getEmail());
        merchant.setAddress(merchantForm.getAddress());
        merchant.setSlogan(merchantForm.getSlogan());

        if (!merchantForm.getAvatarImage().isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(merchantForm.getAvatarImage().getOriginalFilename());
                String uploadDir = "uploads/images/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                merchantForm.getAvatarImage().transferTo(filePath);
                merchant.setAvatarImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        merchantService.save(merchant);

        // Nội dung email
        String subject = "New Merchant Registration";
        String content = "<h3>Thông tin đăng ký merchant:</h3>"
                + "<p><b>Name:</b> " + merchant.getName() + "</p>"
                + "<p><b>Phone:</b> " + merchant.getPhone() + "</p>"
                + "<p><b>Email:</b> " + merchant.getEmail() + "</p>"
                + "<p><b>Address:</b> " + merchant.getAddress() + "</p>"
                + "<p><b>Slogan:</b> " + merchant.getSlogan() + "</p>";

        if (merchant.getAvatarImage() != null) {
            content += "<p><b>Avatar:</b> <a href='http://yourdomain.com/uploads/images/" + merchant.getAvatarImage() + "'>View Image</a></p>";
        }

        try {
            emailService.sendRegistrationEmail("hangrongv25@gmail.com", subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        ModelAndView mav = new ModelAndView("/merchant/home");
        mav.addObject("merchant", merchant);
        return mav;
    }


}
