package com.gdou.dmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.NewsInfo;
import com.gdou.dmall.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
public class NewsController {

    @Value("${ylrc.upload.photo.path}")
    private String uploadPhotoPath;

    @Reference
    NewsService newsService;

    @RequestMapping("getNewsInfo")
    @ResponseBody
    public List<NewsInfo> getNewsInfo(){
        List<NewsInfo> newsInfos = newsService.getNewsInfo();
        return newsInfos;
    }

    @RequestMapping("photoUpload")
    @ResponseBody
    public String photoUpload(@RequestParam("file") MultipartFile multipartFile){

        String originalFilename = multipartFile.getOriginalFilename();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String photoName = sdf.format(d)+"_"+originalFilename;
        //准备保存文件
        File filePath = new File(uploadPhotoPath);
        if(!filePath.exists()){
            //若不存在文件夹，则创建一个文件夹
            filePath.mkdir();
        }

        try {
            multipartFile.transferTo(new File(uploadPhotoPath+"/"+photoName));
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String imgUrl = "http://127.0.0.1:8686/dmall/img/"+photoName;
        return imgUrl;
    }

    @RequestMapping("saveNewsInfo")
    @ResponseBody
    public  String saveNewsInfo(@RequestBody NewsInfo newsInfo){
        newsService.saveNewsInfo(newsInfo);
        return "success";
    }

    @RequestMapping("deleteNewsInfo")
    @ResponseBody
    public  String deleteNewsInfo(@RequestBody NewsInfo newsInfo){
        newsService.deleteNewsInfo(newsInfo);
        return "success";
    }

    @RequestMapping("selectNewsByName")
    @ResponseBody
    public List<NewsInfo> selectNewsByName(String newsName){
        List<NewsInfo> news = newsService.selectNewsByName(newsName);
        return news;
    }
}
