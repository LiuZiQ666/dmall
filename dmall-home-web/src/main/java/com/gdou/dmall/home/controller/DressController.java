package com.gdou.dmall.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.DressBaseinfo;
import com.gdou.dmall.service.DressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class DressController {

    @Reference
    DressService dressService;

    @RequestMapping("getPopularInfo")
    @ResponseBody
    public List<DressBaseinfo> getPopularInfo(){
        List<DressBaseinfo> popularInfos = dressService.getPopularInfo();
        return popularInfos;
    }

    @RequestMapping("getDressInfoByRedis")
    @ResponseBody
    public List<DressBaseinfo> getDressInfoByRedis(){
        List<DressBaseinfo> dressBaseinfos = dressService.getDressInfoByRedis();
        return dressBaseinfos;
    }

    @RequestMapping("getDressInfoByType")
    @ResponseBody
    public List<DressBaseinfo> getDressInfoByType(String occasionId){
        List<DressBaseinfo> dressBaseinfoList = dressService.getDressInfoByType(occasionId);
        return dressBaseinfoList;
    }

    @RequestMapping("getDressInfoById")
    @ResponseBody
    public DressBaseinfo getDressInfoById(String dressId){
        DressBaseinfo dressBaseinfo = dressService.getDressInfoById(dressId);
        return dressBaseinfo;
    }

}
