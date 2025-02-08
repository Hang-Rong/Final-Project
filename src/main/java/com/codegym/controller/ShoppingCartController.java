package com.codegym.controller;

import com.codegym.model.Items;
import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.model.Product;
import com.codegym.service.IOrderDetailService;
import com.codegym.service.IOrderService;
import com.codegym.service.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private IProductService pm;
    @Autowired
    private IOrderService om;
    @Autowired
    private IOrderDetailService odm;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ordernow/{id}", method = RequestMethod.GET)
    public String ordernow(@PathVariable(value = "id") Long id, HttpSession session) {

        if (session.getAttribute("cart") == null) {
            List<Items> cart = new ArrayList<Items>(); // cart
            Optional<Product> product = pm.findById(id);
            cart.add(new Items(product.get(), 1));
            session.setAttribute("cart", cart);
        } else {
            List<Items> cart = (List<Items>) session.getAttribute("cart");
            // using method isExisting here
            int index = isExisting(id, session);
            if (index == -1)
                cart.add(new Items(this.pm.findById(id).get(), 1));
            else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }
        return "/product/cart";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpSession session) {
        List<Items> cart = (List<Items>) session.getAttribute("cart");
        String[] quantities = request.getParameterValues("qty");
        for (int i = 0; i < cart.size(); i++) {
            int quantity = Integer.parseInt(quantities[i]);
            cart.get(i).setQuantity(quantity);
        }
        session.setAttribute("cart", cart);
        return "/product/cart";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "id") Long id, HttpSession session) {
        List<Items> cart = (List<Items>) session.getAttribute("cart");

        int index = isExisting(id, session);
        cart.remove(index);
        session.setAttribute("cart", cart);
        return "/product/cart";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(HttpSession session) {
        List<Items> cart = (List<Items>) session.getAttribute("cart");

        //Add new Order
        Order order = new Order();
        order.setOrderDate(new Date());
        om.save(order);

        //Add new OrderDetail
        for (Items item : cart) {
            Product product = item.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuanity(item.getQuantity());
            odm.save(orderDetail);
        }

        session.removeAttribute("cart");
        return "/product/cart";
    }


    @SuppressWarnings("unchecked")
    private int isExisting(Long id, HttpSession session) {
        List<Items> cart = (List<Items>) session.getAttribute("cart");
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i).getProduct();
            if (product.getId() == id) {
                return i;
            }
        }
        return -1;
    }


//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
//    public String checkout(HttpSession session, ModelMap model) {
//        List<Items> cart = (List<Items>) session.getAttribute("cart");
//
//        // Kiểm tra xem giỏ hàng có trống không
//        if (cart == null || cart.isEmpty()) {
//            model.addAttribute("message", "Giỏ hàng trống, không thể thanh toán.");
//            return "/product/cart"; // Nếu giỏ hàng trống, trả về trang giỏ hàng
//        }
//
//        // Tạo đơn hàng mới
//        Order order = new Order();
//        order.setOrderDate(new Date());
//        om.save(order);
//
//        // Tính tổng giá trị giỏ hàng
//        double totalPrice = 0;
//        for (Items item : cart) {
//            Product product = item.getProduct();
//            totalPrice += product.getPrice() * item.getQuantity(); // Tính tổng giá trị
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//            orderDetail.setProduct(product);
//            orderDetail.setQuanity(item.getQuantity());
//            odm.save(orderDetail);
//        }
//
//        // Cập nhật tổng giá vào đơn hàng
//        order.setTotalPrice(totalPrice);
//        om.save(order);
//
//        // Xóa giỏ hàng trong session sau khi thanh toán thành công
//        session.removeAttribute("cart");
//
//        // Truyền dữ liệu vào model để hiển thị
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("order", order);
//
//        return "/product/checkout";
//
//
//    }
}
