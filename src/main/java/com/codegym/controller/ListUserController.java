package com.codegym.controller;
import com.codegym.model.AppUser;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ListUserController {

    @Autowired
    private IAppUserService appUserService; // Service để lấy danh sách người dùng

    @GetMapping("/listuser")  // Đảm bảo đường dẫn chính xác cho view listuser
    public String listUsers(Model model) {
        Iterable<AppUser> appUsers = appUserService.findAll();  // Lấy tất cả người dùng
        model.addAttribute("list", appUsers);  // Thêm danh sách vào model với tên "list"
        return "listuser";  // Trả về view "user.html" (phải trùng với tên file view)
    }
}
