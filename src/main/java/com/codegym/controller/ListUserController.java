package com.codegym.controller;
import com.codegym.model.AppUser;
import com.codegym.model.Customer;
import com.codegym.service.IAppUserService;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ListUserController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/listuser")
    public String listUsers(Model model) {
        Iterable<Customer> customers = customerService.findAll();
        model.addAttribute("list", customers);
        return "listuser";
    }
}
