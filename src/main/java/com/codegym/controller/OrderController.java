package com.codegym.controller;


import com.codegym.model.Order;
import com.codegym.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/merchan")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/updateStatus/{orderId}", method = RequestMethod.POST)
    public String updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            orderService.save(order);
        }
        return "redirect:/merchan/orderConfirm";
    }
}
