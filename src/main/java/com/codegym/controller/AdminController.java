package com.codegym.controller;
import com.codegym.model.*;
import com.codegym.service.IAppRoleService;
import com.codegym.service.IAppUserService;
import com.codegym.service.ICustomerService;
import com.codegym.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAppRoleService appRoleService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IMerchantService merchantService;

    @GetMapping()
    public String getAllUser(){
        return "/admin";
    }

    @PostMapping("/setrole")
    public String setUserRole(@RequestParam(value = "userId", required = false) Long userId,
                              @RequestParam("role") String role) {
        if (userId == null) {
            return "redirect:/admin/listuser?error=userId_missing";
        }

        Optional<AppUser> optionalUser = appUserService.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();

            try {
                ROLENAME newRole = ROLENAME.valueOf(role);
                user.setLevelOfAuthority(newRole);

                // ✅ Cập nhật quyền khi thay đổi cấp độ
                updateRolesBasedOnLevel(user);

                appUserService.save(user);
            } catch (IllegalArgumentException e) {
                return "redirect:/admin/listuser?error=invalid_role";
            }
        }

        return "redirect:/admin/listuser";
    }

    private void updateRolesBasedOnLevel(AppUser user) {
        user.getRoll().clear(); // Xóa quyền cũ

        AppRole role = appRoleService.findByName(user.getLevelOfAuthority().name()); // Tìm trong DB

        if (role == null) {
            role = new AppRole(user.getLevelOfAuthority().name());
            appRoleService.save(role); // Lưu vào DB nếu chưa có
        }

        user.getRoll().add(role); // Cập nhật quyền mới
    }

    @PostMapping("/delete-customer")
    public String softDeleteCustomer(@RequestParam("customerId") Long customerId) {
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setDeleted(true); // Đánh dấu đã xóa
            customerService.save(customer);
        }

        return "redirect:/admin/listuser"; // Quay lại danh sách khách hàng
    }

    @GetMapping("/new-merchant")
    public String showAddShopForm(Model model) {
        model.addAttribute("merchantForm", new MerchantForm());
        return "/admin/addshop";
    }

    // Xử lý thêm cửa hàng mới
    @PostMapping("/new-merchant")
    public String addNewShop(@ModelAttribute MerchantForm merchantForm) {
        Merchant merchant = new Merchant();
        merchant.setName(merchantForm.getName());
        merchant.setPhone(merchantForm.getPhone());
        merchant.setEmail(merchantForm.getEmail());
        merchant.setAddress(merchantForm.getAddress());
        merchant.setSlogan(merchantForm.getSlogan());

        // Lưu ảnh nếu có
        if (!merchantForm.getAvatarImage().isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + merchantForm.getAvatarImage().getOriginalFilename();
                Path uploadPath = Paths.get("uploads/avatars");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(merchantForm.getAvatarImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                merchant.setAvatarImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        merchantService.save(merchant);
        return "redirect:/admin/shoplist";
    }

    @GetMapping("/shoplist")
    public String shopList(Model model) {
        model.addAttribute("shopList", merchantService.findAll());
        return "/admin/shoplist";
    }

}