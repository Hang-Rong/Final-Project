package com.codegym.controller;
import com.codegym.model.*;
import com.codegym.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private IRequestFormService requestFormService;

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


    @GetMapping("/requests")
    public String showAllRequests(Model model) {
        Iterable<RequestForm> requestForms = requestFormService.findAll();
        model.addAttribute("requestForms", requestForms);
        return "/admin/requests";
    }

    @PostMapping("/markAsRead/{id}")
    public String markAsRead(@PathVariable("id") Long id) {
        Optional<RequestForm> requestFormOptional = requestFormService.findById(id);

        if (requestFormOptional.isPresent()) {
            RequestForm requestForm = requestFormOptional.get();
            requestForm.setRead(true);
            requestFormService.save(requestForm);
        }

        return "redirect:/admin/requests";
    }

   @PostMapping("/markAsUnread/{id}")
   public String markAsUnread(@PathVariable("id") Long id) {
        Optional<RequestForm> requestFormOptional = requestFormService.findById(id);
        if (requestFormOptional.isPresent()) {
            RequestForm requestForm = requestFormOptional.get();
            requestForm.setRead(false);
            requestFormService.save(requestForm);
        }
            return "redirect:/admin/requests";
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
        model.addAttribute("merchantForm", new Merchant());
        return "/admin/addshop";
    }

    // Xử lý thêm cửa hàng mới
    @PostMapping("/new-merchant")
    public String addNewShop(@ModelAttribute Merchant merchant) {
        merchant.setName(merchant.getName());
        merchant.setPhone(merchant.getPhone());
        merchant.setEmail(merchant.getEmail());
        merchant.setAddress(merchant.getAddress());
        merchant.setSlogan(merchant.getSlogan());

//        // Lưu ảnh nếu có
//        if (!merchant.getAvatarImage().isEmpty()) {
//            try {
//                String fileName = UUID.randomUUID().toString() + "_" + merchant.getAvatarImage().getOriginalFilename();
//                Path uploadPath = Paths.get("uploads/avatars");
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//                Files.copy(merchantForm.getAvatarImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//                merchant.setAvatarImage(fileName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        merchantService.save(merchant);
        return "redirect:/admin/shoplist";
    }

    @GetMapping("/shoplist")
    public String shopList(Model model) {
        model.addAttribute("shopList", merchantService.findAll());
        return "admin/shoplist";
    }

}