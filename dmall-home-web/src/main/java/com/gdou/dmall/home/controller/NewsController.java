package com.gdou.dmall.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gdou.dmall.bean.NewsInfo;
import com.gdou.dmall.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class NewsController {

    @Reference
    NewsService newsService;

    @RequestMapping("getNewsInfoByRedis")
    @ResponseBody
    public List<NewsInfo> getNewsInfoByRedis(){
        List<NewsInfo> newsInfos = newsService.getNewsInfoByRedis();
        return newsInfos;
    }

    @RequestMapping("getNewsItemByRedis")
    @ResponseBody
    public NewsInfo getNewsItemByRedis(String newsId){
        NewsInfo newsInfo = newsService.getNewsItemByRedis(newsId);
        return newsInfo;
    }
}
