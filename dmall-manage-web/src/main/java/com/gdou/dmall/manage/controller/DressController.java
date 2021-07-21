package com.gdou.dmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.DressBaseinfo;
import com.gdou.dmall.bean.DressImage;
import com.gdou.dmall.service.DressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class DressController {

    @Reference
    DressService dressService;

    @RequestMapping("getDressBaseinfo")
    @ResponseBody
    public List<DressBaseinfo> getDressBaseinfo(){
        List<DressBaseinfo> dressBaseinfos = dressService.getDressBaseinfo();
        return dressBaseinfos;
    }

    @RequestMapping("getDressImage")
    @ResponseBody
    public List<DressImage> getDressImage(String dressId){
        List<DressImage> dressImages = dressService.getDressImage(dressId);
        return dressImages;
    }

    @RequestMapping("deleteDress")
    @ResponseBody
    public String deleteDress( String dressId){
        dressService.deleteDress(dressId);
        return "seccuss";
    }

    @RequestMapping("saveDress")
    @ResponseBody
    public void saveDress(@RequestBody DressBaseinfo dressBaseinfo){
        dressService.saveDress(dressBaseinfo);
    }

    @RequestMapping("selectDressById")
    @ResponseBody
    public List<DressBaseinfo> selectDressById(String dressId){
        List<DressBaseinfo> dress = dressService.selectDressById(dressId);
        return dress;
    }

    @RequestMapping("selectDressByName")
    @ResponseBody
    public List<DressBaseinfo> selectDressByName(String dressName){
        List<DressBaseinfo> dress = dressService.selectDressByName(dressName);
        return dress;
    }
}
