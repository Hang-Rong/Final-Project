package com.codegym.controller;

import com.codegym.model.AppUser;
import com.codegym.model.Product;
import com.codegym.model.ROLENAME;
import com.codegym.service.IAppUserService;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private IAppUserService appUserService;

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

        String redirectUrl = "/product-details/" + productId;
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();
    }

    @PostMapping("/review/hide")
    public ResponseEntity<?> hideReview(@RequestParam("productId") Long productId,
                                        @RequestParam("commentIndex") int commentIndex,
                                        Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName()).orElse(null);

        if (user == null || !user.getLevelOfAuthority().equals(ROLENAME.ROLE_ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền ẩn bình luận");
        }

        Product product = productService.findById(productId).orElse(null);
        if (product == null || product.getComments() == null || product.getComments().isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm hoặc bình luận");
        }

        String[] comments = product.getComments().split("\n");

        if (commentIndex < 0 || commentIndex >= comments.length) {
            return ResponseEntity.badRequest().body("Chỉ số bình luận không hợp lệ");
        }

        // Chuyển bình luận sang trạng thái "HIDDEN"
        String[] parts = comments[commentIndex].split(" - ", 5);
        if (parts.length < 5) {
            return ResponseEntity.badRequest().body("Lỗi định dạng bình luận");
        }

        parts[3] = "HIDDEN"; // Đổi trạng thái thành ẩn
        comments[commentIndex] = String.join(" - ", parts);

        product.setComments(String.join("\n", comments));
        productService.save(product);

        return ResponseEntity.ok("Bình luận đã bị ẩn");
    }
}
