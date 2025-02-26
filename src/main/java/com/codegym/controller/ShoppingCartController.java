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
import org.springframework.web.bind.annotation.GetMapping;
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
    public String ordernow(@PathVariable(value = "id") Long id, HttpSession session, ModelMap model) {

        Optional<Product> productOpt = pm.findById(id);

        if (!productOpt.isPresent()) {
            return "/product/cart";
        }

        Product product = productOpt.get();
        if (product.isOutOfStock()) {
            model.addAttribute("message", "Sản phẩm này đã ngừng bán.");
            return "/product/cart";

        }

        if (session.getAttribute("cart") == null) {
            List<Items> cart = new ArrayList<>();
            cart.add(new Items(product, 1));
            session.setAttribute("cart", cart);
        } else {
            List<Items> cart = (List<Items>) session.getAttribute("cart");
            int index = isExisting(id, session);
            if (index == -1)
                cart.add(new Items(product, 1));
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
    public String checkout(HttpSession session, ModelMap model) {
        List<Items> cart = (List<Items>) session.getAttribute("cart");

        // Tính tổng số tiền cần thanh toán
        double totalPrice = 0.0;
        for (Items item : cart) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            totalPrice += product.getPrice() * quantity; // Giả sử Product có thuộc tính price
        }

        // Tạo mới một đơn hàng
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setTotalPrice(totalPrice); // Cập nhật tổng số tiền vào đơn hàng
        om.save(order); // Lưu đơn hàng

        // Thêm chi tiết đơn hàng
        for (Items item : cart) {
            Product product = item.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuanity(item.getQuantity());
            odm.save(orderDetail); // Lưu chi tiết đơn hàng
        }

        // Xóa giỏ hàng sau khi thanh toán
        session.removeAttribute("cart");
        model.addAttribute("order", order); // Thêm đơn hàng vào model
        model.addAttribute("orderDetails", odm.findByOrderId(order.getId()));
        // Truyền tổng giá trị vào model để hiển thị trên giao diện
        model.addAttribute("totalPrice", totalPrice);


        return "/product/showbill";
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


}
