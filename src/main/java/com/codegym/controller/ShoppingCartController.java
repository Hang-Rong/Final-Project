package com.codegym.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.codegym.model.Items;
import com.codegym.model.Product;
import com.codegym.model.Order;
import com.codegym.model.OrderDetail;



import com.codegym.service.IProductService;
import com.codegym.service.IOrderService;
import com.codegym.service.IOrderDetailService;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(HttpSession session) {
        List<Items> cart = (List<Items>) session.getAttribute("cart");

        Order order = new Order();
        order.setOrderDate(new Date());
        om.save(order);

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

}
