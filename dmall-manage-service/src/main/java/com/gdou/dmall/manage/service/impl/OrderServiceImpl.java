package com.gdou.dmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gdou.dmall.bean.OrderInfo;
import com.gdou.dmall.manage.mapper.OrderInfoMapper;
import com.gdou.dmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> getOrderInfo() {
        return orderInfoMapper.selectAll();
    }

    @Override
    public void saveOrder(OrderInfo orderInfo) {
        orderInfoMapper.insertSelective(orderInfo);
    }

    @Override
    public List<OrderInfo> selectOrderByName(String name) {
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String dName = "%" + name + "%";
        criteria.andLike("name",dName);
        return orderInfoMapper.selectByExample(example);
    }
}
