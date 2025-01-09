package com.codegym.service;

import com.codegym.model.OrderDetail;
import com.codegym.repository.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailService implements IOrderDetailService{
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    @Override
    public Iterable<OrderDetail> findAll() {
        return iOrderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return iOrderDetailRepository.findById(id);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        iOrderDetailRepository.save(orderDetail);
    }

    @Override
    public void remove(Long id) {
        iOrderDetailRepository.deleteById(id);
    }
}