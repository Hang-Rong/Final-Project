package com.codegym.service.impl;


import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.repository.IOrderDetailRepository;
import com.codegym.repository.IOrderRepository;
import com.codegym.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    @Override
    public Iterable<Order> findAll() {
        return iOrderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return iOrderRepository.findById(id);
    }

    @Override
    public void save(Order order) {
        iOrderRepository.save(order);
    }

    @Override
    public void remove(Long id) {
        iOrderRepository.deleteById(id);
    }

    public double calculateTotalPrice(Order order) {
        List<OrderDetail> orderDetails = iOrderDetailRepository.findByOrder(order);  // Lấy tất cả OrderDetail cho Order này
        double total = 0;

        for (OrderDetail orderDetail : orderDetails) {
            double productPrice = orderDetail.getProduct().getPrice();
            int quantity = orderDetail.getQuanity();
            total += productPrice * quantity;
        }

        return total;
    }

}