package com.gdou.dmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.MessageInfo;
import com.gdou.dmall.bean.OccasionInfo;
import com.gdou.dmall.service.OccaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class OccaController {

    @Reference
    OccaService occaService;

    @RequestMapping("getOccasionInfo")
    @ResponseBody
    public List<OccasionInfo> getOccasionInfo(){
        List<OccasionInfo> occasionInfos = occaService.getOccasionInfo();
        return occasionInfos;
    }

    @RequestMapping("saveOccasionInfo")
    @ResponseBody
    public  int saveOccasionInfo(@RequestBody OccasionInfo occasionInfo){
        int flag = occaService.selectOccasion(occasionInfo);
        if(flag != 0){
            return flag;
        }
        occaService.saveOccasionInfo(occasionInfo);
        return flag;
    }
}
