package com.gdou.dmall.service;

import com.gdou.dmall.bean.OccasionInfo;

import java.util.List;

public interface OccaService {
    List<OccasionInfo> getOccasionInfo();

    void saveOccasionInfo(OccasionInfo occasionInfo);

    int selectOccasion(OccasionInfo occasionInfo);
}
