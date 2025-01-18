package com.codegym.service;

import com.codegym.model.OrderDetail;

public interface IOrderDetailService extends IGeneralService<OrderDetail>{
    Iterable<OrderDetail> getOrderDetailsByOrderId(int orderId);

}
