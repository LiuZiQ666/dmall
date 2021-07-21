package com.gdou.dmall.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.OrderInfo;
import com.gdou.dmall.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class BookController {

    @Reference
    OrderService orderService;

    @RequestMapping("saveOrder")
    @ResponseBody
    public String saveOrder(@RequestBody OrderInfo orderInfo){
        orderService.saveOrder(orderInfo);
        return "seccuss";
    }
}
