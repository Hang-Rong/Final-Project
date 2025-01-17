package com.codegym.controller;

import com.codegym.model.Merchant;
import com.codegym.model.MerchantForm;
import com.codegym.model.Product;
import com.codegym.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView("/merchant/register");
        mav.addObject("merchant", new Merchant());
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

        ModelAndView mav = new ModelAndView("/merchant/home");
        mav.addObject("merchant", merchant);
        return mav;
    }


}
