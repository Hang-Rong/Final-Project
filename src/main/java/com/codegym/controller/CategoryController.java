package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.ICategoryService;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    public String listCategory(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/products/{categoryId}")
    public String listProductsByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        // Lấy category từ ID
        Category category = categoryService.findById(categoryId).orElse(null);

        if (category == null) {
            return "redirect:/categories/list";
        }

        // Lấy các sản phẩm của category này
        Iterable<Product> products = productService.findByCategory(category);

        model.addAttribute("category", category);
        model.addAttribute("products", products);

        return "product/listByCategory";
    }



}
