package com.gdou.dmall.service;

import com.gdou.dmall.bean.MessageInfo;

import java.util.List;

public interface MsgService {
     List<MessageInfo> getMessageInfo();

    void saveMessageInfo(MessageInfo messageInfo);
}
