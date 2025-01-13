package com.codegym.controller;

import com.codegym.model.CustomerAccount;
import com.codegym.model.Review;
import com.codegym.model.Product;
import com.codegym.repository.IReviewRepository;
import com.codegym.service.ICustomerAccountService;
import com.codegym.service.IProductService;
import com.codegym.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private ICustomerAccountService customerAccountService;

    @GetMapping("/view/{id}")
    public ModelAndView viewProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        List<com.codegym.model.Review> reviews = reviewRepository.findByProductId(id);

        ModelAndView modelAndView = new ModelAndView("/product/view");
        modelAndView.addObject("product", product);
        modelAndView.addObject("reviews", reviews);
        return modelAndView;
    }

    @PostMapping("/review/{productId}")
    public String addReview(@PathVariable("productId") Long productId,
                            @RequestParam("author") String author,
                            @RequestParam("comment") String comment) {
        Optional<Product> productOpt = productService.findById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Review review = new Review();
            review.setAuthor(author);
            review.setComment(comment);
            review.setProduct(product);
            review.setDate(new Date());
            reviewRepository.save(review);
        } else {
            return "redirect:error/404";
        }
        return "redirect:/products/detail/" + productId;
    }
}
