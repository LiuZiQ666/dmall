package com.gdou.dmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gdou.dmall.bean.OccasionInfo;
import com.gdou.dmall.manage.mapper.OccasionInfoMapper;
import com.gdou.dmall.service.OccaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class OccaServiceImpl implements OccaService {

    @Autowired
    OccasionInfoMapper occasionInfoMapper;

    @Override
    public List<OccasionInfo> getOccasionInfo() {
        return occasionInfoMapper.selectAll();
    }

    @Override
    public void saveOccasionInfo(OccasionInfo occasionInfo) {
        occasionInfoMapper.insertSelective(occasionInfo);
    }

    @Override
    public int selectOccasion(OccasionInfo occasionInfo) {
        return occasionInfoMapper.selectCount(occasionInfo);
    }

}
