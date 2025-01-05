package com.codegym.controller;

import com.codegym.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    ITransactionService transactionService;


    @GetMapping("")
    public ModelAndView listTransaction() {
        ModelAndView mav = new ModelAndView("transaction/list");
        return mav;
    }


}
