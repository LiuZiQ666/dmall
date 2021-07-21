package com.gdou.dmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.User;
import com.gdou.dmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@CrossOrigin
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("ComparisonUser")
    @ResponseBody
    public int ComparisonUser(@RequestBody User user){
        return userService.ComparisonUser(user);
    }

    @RequestMapping("changePassword")
    @ResponseBody
    public int changePassword(String userName,String oldPassword,String newPassword){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(oldPassword);
        int flag = userService.ComparisonUser(user);
        if(flag == 0){
            return 0;
        }
        user.setPassword(newPassword);
        userService.changePassword(user);
        return flag;
    }
}
