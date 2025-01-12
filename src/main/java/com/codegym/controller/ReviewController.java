package com.codegym.controller;


import com.codegym.model.Review;
import com.codegym.model.Product;
import com.codegym.repository.IReviewRepository;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IReviewRepository reviewRepository;
    @Autowired
    private IReviewRepository Review;

    @GetMapping("/view/{id}")
    public ModelAndView viewProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        List<com.codegym.model.Review> reviews = reviewRepository.findByProductId(id);

        ModelAndView modelAndView = new ModelAndView("/product/view");
        modelAndView.addObject("product", product);
        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("newReview", new Review());
        return modelAndView;
    }

    @PostMapping("/review/{productId}")
    public String addReview(@PathVariable("productId") Long productId,
                            @ModelAttribute("newReview") Review review) {
        Product product = productService.findById(productId).orElse(null);
        if (product != null) {
            review.setProduct(product);
            review.setReviewDate(new Date());
            reviewRepository.save(review);
        }
        return "redirect:/products/view/" + productId;
    }
}
