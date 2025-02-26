package com.codegym.service;

import com.codegym.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService extends IGeneralService<OrderDetail>{
    List<OrderDetail> findByOrderId(Long orderId);
}
