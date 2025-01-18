package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import com.codegym.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/customer/profile");
        Optional<Customer> cus = customerService.findById(id);
        mav.addObject("customer", cus);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("/customer/create");
        mav.addObject("customer", new Customer());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer,
                                     @RequestParam("image") MultipartFile image) {
        ModelAndView mav = new ModelAndView("redirect:"+"/customer/create");
        String fileName = image.getOriginalFilename();
        Byte[] files = image.getBytes();
        customer.setAvatarImage(fileName);
        customerService.save(customer);
        mav.addObject("customer", customer);
        return mav;
    }
}
