package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product-details")
public class ProductDetailsController {
    @Autowired
    private IProductService productService;

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Chuyển đổi comment thành danh sách để dễ hiển thị
            List<String> reviews = new ArrayList<>();
            if (product.getComments() != null && !product.getComments().isEmpty()) {
                reviews = Arrays.asList(product.getComments().split("\n"));
            }

            model.addAttribute("product", product);
            model.addAttribute("reviews", reviews);
            return "product/details";
        } else {
            return "product/not-found";
        }
    }
}
