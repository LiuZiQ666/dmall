package com.gdou.dmall.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.OccasionInfo;
import com.gdou.dmall.service.OccaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class OccasionController {

    @Reference
    OccaService occaService;

    @RequestMapping("getOccasionInfo")
    @ResponseBody
    public List<OccasionInfo> getOccasionInfo(){
        List<OccasionInfo> occasionInfos = occaService.getOccasionInfo();
        return occasionInfos;
    }
}
