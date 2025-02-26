package com.codegym.controller;
import com.codegym.model.AppUser;
import com.codegym.model.Customer;
import com.codegym.service.IAppUserService;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ListUserController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/listuser")
    public String listUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Customer> customers = customerService.getAllCustomers(); // Lấy danh sách hợp lệ
        model.addAttribute("list", customers);
        model.addAttribute("currentUsername", userDetails.getUsername());
        return "listuser";
    }

    @GetMapping("/deleted-users")
    public String listDeletedUsers(Model model) {
        List<Customer> deletedCustomers = customerService.getDeletedCustomers();
        model.addAttribute("deletedList", deletedCustomers);
        return "deleted-users";
    }

    @PostMapping("/restore")
    public String restoreUser(@RequestParam("userId") Long userId) {
        Optional<Customer> customerOptional = customerService.findById(userId);
        customerOptional.ifPresent(customer -> {
            customer.setDeleted(false); // Đặt lại trạng thái chưa xóa
            customerService.save(customer);
        });
        return "redirect:/admin/deleted-users";
    }
}
