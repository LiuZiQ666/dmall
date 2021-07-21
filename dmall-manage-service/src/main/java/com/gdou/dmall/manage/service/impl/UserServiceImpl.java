package com.gdou.dmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gdou.dmall.bean.User;
import com.gdou.dmall.manage.mapper.UserMapper;
import com.gdou.dmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int ComparisonUser(User user) {
        return userMapper.selectCount(user);
    }

    @Override
    public int changePassword(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",user.getUserName());
        return userMapper.updateByExampleSelective(user,example);
    }
}
