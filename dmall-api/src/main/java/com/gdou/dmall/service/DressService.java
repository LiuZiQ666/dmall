package com.gdou.dmall.service;

import com.gdou.dmall.bean.DressBaseinfo;
import com.gdou.dmall.bean.DressImage;

import java.util.List;

public interface DressService {
    List<DressBaseinfo> getDressBaseinfo();

    List<DressImage> getDressImage(String dressId);

    void deleteDress(String dressId);

    void saveDress(DressBaseinfo dressBaseinfo);

    List<DressBaseinfo> getPopularInfo();

    List<DressBaseinfo> getDressInfoByRedis();

    List<DressBaseinfo> getDressInfoByType(String occasionId);

    DressBaseinfo getDressInfoById(String dressId);

    List<DressBaseinfo> selectDressById(String dressId);

    List<DressBaseinfo> selectDressByName(String dressName);
}
