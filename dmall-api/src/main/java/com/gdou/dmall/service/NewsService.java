package com.gdou.dmall.service;

import com.gdou.dmall.bean.NewsInfo;

import java.util.List;

public interface NewsService {
    List<NewsInfo> getNewsInfo();

    void saveNewsInfo(NewsInfo newsInfo);

    void deleteNewsInfo(NewsInfo newsInfo);

    List<NewsInfo> getNewsInfoByRedis();

    NewsInfo getNewsItemByRedis(String newsId);

    List<NewsInfo> selectNewsByName(String newsName);
}
