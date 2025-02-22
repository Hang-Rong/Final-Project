package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.IAppUserService;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private IProductService productService;

    @PostMapping("/review/{productId}")
    public ResponseEntity<?> createReview(@PathVariable Long productId,
                                          @RequestParam("comment") String comment,
                                          @RequestParam("rating") Integer rating,
                                          Principal principal) {
        String username = principal.getName();
        Product product = productService.findById(productId).orElse(null);

        if (product == null) {
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại");
        }

        if (rating < 1 || rating > 5) {
            return ResponseEntity.badRequest().body("Điểm đánh giá phải từ 1 đến 5");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String formattedDate = formatter.format(new Date());
        String formattedComment = username + " - " + formattedDate + " - " + rating + "⭐ - " + comment;

        if (product.getComments() != null && !product.getComments().isEmpty()) {
            product.setComments(product.getComments() + "\n" + formattedComment);
        } else {
            product.setComments(formattedComment);
        }

        // ✅ Kiểm tra và gán giá trị mặc định nếu null
        if (product.getAllReviews() == null) {
            product.setAllReviews(0);
        }
        if (product.getAllRating() == null) {
            product.setAllRating(0);
        }

        // ✅ Cập nhật tổng số lượt đánh giá và tổng điểm
        product.setAllReviews(product.getAllReviews() + 1);
        product.setAllRating(product.getAllRating() + rating);

        productService.save(product);

        return ResponseEntity.ok("Đánh giá đã được lưu");
    }
}
