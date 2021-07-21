package com.gdou.dmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gdou.dmall.bean.MessageInfo;
import com.gdou.dmall.manage.mapper.MessageInfoMapper;
import com.gdou.dmall.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    MessageInfoMapper messageInfoMapper;

    @Override
    public List<MessageInfo> getMessageInfo() {
        return messageInfoMapper.selectAll();
    }

    @Override
    public void saveMessageInfo(MessageInfo messageInfo) {
        MessageInfo messageInfo1 = new MessageInfo();
        messageInfo1.getId(); 
        messageInfoMapper.delete(messageInfo1);
        messageInfoMapper.insertSelective(messageInfo);
    }

}
