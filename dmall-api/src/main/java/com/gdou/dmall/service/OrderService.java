package com.gdou.dmall.service;

import com.gdou.dmall.bean.OrderInfo;

import java.util.List;

public interface OrderService {
    List<OrderInfo> getOrderInfo();

    void saveOrder(OrderInfo orderInfo);

    List<OrderInfo> selectOrderByName(String name);
}
