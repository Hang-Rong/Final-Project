package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }
//Lưu sp moi
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
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
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
        product.setId(id);  // Đảm bảo ID không bị thay đổi khi chỉnh sửa
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

    // Xóa sp (Delete)
    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        productService.remove(id);
        return new ModelAndView("redirect:/products");
    }
    // Hiển thị ds sp
    @GetMapping
    public ModelAndView listProducts(@RequestParam(value = "page", defaultValue = "0") int page,
<<<<<<< HEAD
                                     @RequestParam(value = "size", defaultValue = "3") int size) {
=======
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
>>>>>>> 57f7fda445cb61cd7042ca40aa230d1f4cab130f
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
<<<<<<< HEAD
        product.setId(id);
=======
        product.setId(id);  // Đảm bảo ID không bị thay đổi khi chỉnh sửa
>>>>>>> 57f7fda445cb61cd7042ca40aa230d1f4cab130f
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

    // Xóa sp (Delete)
    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        productService.remove(id);
        return new ModelAndView("redirect:/products");
    }
<<<<<<< HEAD
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
=======
>>>>>>> 57f7fda445cb61cd7042ca40aa230d1f4cab130f
}
