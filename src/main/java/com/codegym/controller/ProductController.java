package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Merchant;
import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import com.codegym.service.ICategoryService;
import com.codegym.service.IProductService;
import com.codegym.service.impl.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private MerchantService merchantService;

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }



    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("product") Product product,
                                    @RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                String uploadDir = "uploads/images/";
                Path path = Paths.get(uploadDir + image.getOriginalFilename());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                product.setImageName(image.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Long categoryId = product.getCategory().getId();
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category != null) {
            product.setCategory(category);
        }



        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("categories", categoryService.findAll());

        return modelAndView;
    }


    // Hiển thị danh sách sản phẩm chưa bị xóa
    @GetMapping
    public ModelAndView listProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "8") int size) {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", productService.findByIsDeletedFalse(PageRequest.of(page, size)));
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

    @PostMapping("/edit/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id,
                                      @ModelAttribute("product") Product product,
                                      @RequestParam(value = "image", required = false) MultipartFile image) {
        // Cập nhật thông tin sản phẩm
        product.setId(id);

        // Kiểm tra nếu có hình ảnh mới thì upload
        if (image != null && !image.isEmpty()) {
            try {
                String uploadDir = "uploads/images/";
                Path path = Paths.get(uploadDir + image.getOriginalFilename());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật tên hình ảnh mới
                product.setImageName(image.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Lưu sản phẩm đã cập nhật
        productService.save(product);

        // Chuyển hướng về trang sản phẩm của danh mục sản phẩm
        return new ModelAndView("redirect:/categories/products/" + product.getCategory().getId());
    }



    // Xóa mềm sản phẩm (đánh dấu isDeleted = true)
    @GetMapping("/delete/{id}")
    public ModelAndView softDeleteProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        if (product != null) {
            product.setDeleted(true);
            productService.save(product);
        }
        return new ModelAndView("redirect:/categories/list");
    }


    // ngưng bán sản pohẩm
    @GetMapping("/toggle-outofstock/{id}")
    public ModelAndView toggleOutOfStock(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        if (product != null) {
            product.setOutOfStock(!product.isOutOfStock());
            productService.save(product);
        }
        return new ModelAndView("redirect:/categories/list");
    }

    @GetMapping("/search")
    public ModelAndView searchProducts(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "8") int size) {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", productService.findAllByNameContainingAndIsDeletedFalse(PageRequest.of(page, size), name));
        modelAndView.addObject("searchName", name);
        return modelAndView;
    }

}




