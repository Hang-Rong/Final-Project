package com.codegym.controller;

import com.codegym.model.CustomerAccount;
import com.codegym.model.Product;
import com.codegym.model.Review;
import com.codegym.repository.CustomerAccountRepository;
import com.codegym.repository.IReviewRepository;
import com.codegym.service.ICustomerAccountService;
import com.codegym.service.IProductService;
import com.codegym.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private CustomerAccountRepository customerAccountRepo;
    @Autowired
    private IReviewRepository reviewRepo;

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }
//Lưu sp moi
    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }
    // Hiển thị ds sp
    @GetMapping
    public ModelAndView listProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "3") int size) {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", productService.findAll(PageRequest.of(page, size)));
        return modelAndView;
    }

    // Hiển thị form update
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    // Lưu chỉnh sửa sp (Update)
    @PostMapping("/edit/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        product.setId(id);
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

    // Xóa sp (Delete)
    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        productService.remove(id);
        return new ModelAndView("redirect:/products");
    }
// Search
    @GetMapping("/search")
    public ModelAndView searchProducts(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", productService.findAllByNameContaining(PageRequest.of(page, size), name));
        modelAndView.addObject("searchName", name);
        return modelAndView;
    }

    // detail

    @GetMapping("/detail/{id}")
    public ModelAndView viewProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView("product/detail");

        List<Review> reviews = reviewRepo.findByProductId(id);
        List<CustomerAccount> customers = customerAccountRepo.findAll();

        modelAndView.addObject("product", product);
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("newReview", new Review());
        return modelAndView;
    }
}
