package com.gdou.dmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.MessageInfo;
import com.gdou.dmall.service.MsgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class MsgController {

    @Reference
    MsgService msgService;

    @RequestMapping("getMessageInfo")
    @ResponseBody
    public List<MessageInfo> getMessageInfo(){
        List<MessageInfo> messageInfo = msgService.getMessageInfo();
        return messageInfo;
    }

    @RequestMapping("saveMessageInfo")
    @ResponseBody
    public  String saveMessageInfo(@RequestBody MessageInfo messageInfo){
        msgService.saveMessageInfo(messageInfo);
        return "success";
    }



}
