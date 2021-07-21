package com.gdou.dmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.OrderInfo;
import com.gdou.dmall.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class OrderController {

    @Reference
    OrderService orderService;

    @RequestMapping("getOrderInfo")
    @ResponseBody
    public List<OrderInfo> getOrderInfo(){
        List<OrderInfo> orderInfos = orderService.getOrderInfo();
        return orderInfos;
    }

    @RequestMapping("selectOrderByName")
    @ResponseBody
    public List<OrderInfo> selectOrderByName(String name){
        List<OrderInfo> orderInfoList = orderService.selectOrderByName(name);
        return orderInfoList;
    }
}
