package com.codegym.controller;

import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import com.codegym.service.IOrderDetailService;
import com.codegym.service.IOrderService;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @GetMapping("/list")
    public ModelAndView showOrders() {
        Iterable<Order> orders = orderService.findAll();
        ModelAndView mav = new ModelAndView("order/list");
        mav.addObject("orders", orders);
        return mav;
    }

    @GetMapping("detail/{id}")
    public ModelAndView showOrderDetail(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("order/orderdetail");
        Iterable<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
        mav.addObject("orderId", id);
        mav.addObject("orderDetails",orderDetails);
        return mav;

    }

}
