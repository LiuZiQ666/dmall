package com.gdou.dmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gdou.dmall.bean.DressBaseinfo;
import com.gdou.dmall.bean.DressImage;
import com.gdou.dmall.manage.mapper.DressBaseinfoMapper;
import com.gdou.dmall.manage.mapper.DressImageMapper;
import com.gdou.dmall.service.DressService;
import com.gdou.dmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DressServiceImpl implements DressService {

    @Autowired
    DressBaseinfoMapper dressBaseinfoMapper;
    @Autowired
    DressImageMapper dressImageMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<DressBaseinfo> getDressBaseinfo() {
        return dressBaseinfoMapper.selectAll();
    }

    @Override
    public List<DressImage> getDressImage(String dressId) {
        DressImage dressImage = new DressImage();
        dressImage.setDressId(dressId);
        List<DressImage> dressImages = dressImageMapper.select(dressImage);
        return dressImages;
    }

    @Override
    public void deleteDress(String dressId) {
        DressImage dressImage = new DressImage();
        dressImage.setDressId(dressId);
        dressImageMapper.delete(dressImage);

        Example example = new Example(DressBaseinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",dressId);
        dressBaseinfoMapper.deleteByExample(example);

//        Jedis jedis = redisUtil.getJedis();
//        String newsKey = "popularDress:infoList";
//        jedis.del(newsKey);
//        Set<String> set = jedis.keys("dress*");
//        Iterator<String> it = set.iterator();
//        while(it.hasNext()){
//            jedis.del(it.next());
//        }
//
//        jedis.close();

        DressBaseinfo dressBaseinfo = dressBaseinfoMapper.selectByPrimaryKey(dressId);
        deleteRedis(dressBaseinfo);
    }

    private void deleteRedis(DressBaseinfo dressBaseinfo) {
        Jedis jedis = redisUtil.getJedis();
        if (dressBaseinfo.getViews() >= 10000) {
            String newsKey1 = "popularDress:infoList";
            jedis.del(newsKey1);
        }
        String newsKey2 = "dress:infoList";
        jedis.del(newsKey2);
        String newsKey3 = "dress:" + dressBaseinfo.getOccasionId() + ":infoList";
        jedis.del(newsKey3);

        jedis.close();
    }

    @Override
    public void saveDress(DressBaseinfo dressBaseinfo) {

        String id = dressBaseinfo.getId();
        if(StringUtils.isBlank(id)){
            dressBaseinfoMapper.insertSelective(dressBaseinfo);

            List<DressImage> dressImages = dressBaseinfo.getDressImage();
            for(DressImage dressImage : dressImages){
                dressImage.setDressId(dressBaseinfo.getId());
                dressImageMapper.insertSelective(dressImage);
            }
        }else{
            Example example = new Example(DressBaseinfo.class);
            example.createCriteria().andEqualTo("id",dressBaseinfo.getId());
            dressBaseinfoMapper.updateByExampleSelective(dressBaseinfo,example);

            List<DressImage> dressImages = dressBaseinfo.getDressImage();
            DressImage dressImage = new DressImage();
            dressImage.setDressId(dressBaseinfo.getId());
            dressImageMapper.delete(dressImage);
            for(DressImage dressImage1 : dressImages){
                dressImage1.setDressId(id);
                dressImageMapper.insertSelective(dressImage1);
            }
        }
        Jedis jedis = redisUtil.getJedis();
        String newsKey1 = "popularDress:infoList";
        jedis.del(newsKey1);
        String newsKey2 = "dress:infoList";
        jedis.del(newsKey2);
        String newsKey3 = "dress:"+dressBaseinfo.getOccasionId()+":infoList";
        jedis.del(newsKey3);

        jedis.close();
    }

    @Override
    public List<DressBaseinfo> getPopularInfo() {
//        Example example = new Example(DressBaseinfo.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andGreaterThanOrEqualTo("views",10000);
//        List<DressBaseinfo> popularInfo = dressBaseinfoMapper.selectByExample(example);
//        return popularInfo;
        List<DressBaseinfo> popularInfo;

        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String dressKey = "popularDress:infoList";
        String dressJson = jedis.get(dressKey);

        if(StringUtils.isNotBlank(dressJson)){
            popularInfo = JSON.parseObject(dressJson,new TypeReference<List<DressBaseinfo>>(){} );
        }else{
            // 如果缓存中没有，查询mysql
            // 设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("dress:lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                Example example = new Example(DressBaseinfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andGreaterThanOrEqualTo("views",10000);
                popularInfo = dressBaseinfoMapper.selectByExample(example);

                if(popularInfo!=null){
                    // mysql查询结果存入redis
                    jedis.set("popularDress:infoList", JSON.toJSONString(popularInfo));
                }else{
                    // 数据库中不存在数据
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis
                    jedis.setex("popularDress:infoList",60*3,JSON.toJSONString(""));
                }

                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("dress:lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    jedis.del("dress:lock");// 用token确认删除的是自己的锁
                }

            }else{
                // 设置失败，自旋（重新尝试访问本方法）

                return getPopularInfo();
            }
        }

        jedis.close();
        return popularInfo;
    }

    @Override
    public List<DressBaseinfo> getDressInfoByRedis() {
//        List<DressBaseinfo> dressBaseinfo = getDressBaseinfo();
//        return dressBaseinfo;
        List<DressBaseinfo> dressBaseinfo;

        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String dressKey = "dress:infoList";
        String dressJson = jedis.get(dressKey);

        if(StringUtils.isNotBlank(dressJson)){
            dressBaseinfo = JSON.parseObject(dressJson,new TypeReference<List<DressBaseinfo>>(){} );
        }else{
            // 如果缓存中没有，查询mysql
            // 设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("dress:lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                dressBaseinfo = getDressBaseinfo();

                if(dressBaseinfo!=null){
                    // mysql查询结果存入redis
                    jedis.set("dress:infoList", JSON.toJSONString(dressBaseinfo));
                }else{
                    // 数据库中不存在数据
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis
                    jedis.setex("dress:infoList",60*3,JSON.toJSONString(""));
                }

                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("dress:lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    jedis.del("dress:lock");// 用token确认删除的是自己的锁
                }

            }else{
                // 设置失败，自旋（重新尝试访问本方法）

                return getDressInfoByRedis();
            }
        }

        jedis.close();
        return dressBaseinfo;
    }

    @Override
    public List<DressBaseinfo> getDressInfoByType(String occasionId) {
//        Example example = new Example(DressBaseinfo.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("occasionId",occasionId);
//        List<DressBaseinfo> dressBaseinfoList = dressBaseinfoMapper.selectByExample(example);
//        return dressBaseinfoList;
        List<DressBaseinfo> dressBaseinfoList;

        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String dressKey = "dress:"+occasionId+":infoList";
        String dressJson = jedis.get(dressKey);

        if(StringUtils.isNotBlank(dressJson)){
            dressBaseinfoList = JSON.parseObject(dressJson,new TypeReference<List<DressBaseinfo>>(){} );
        }else{
            // 如果缓存中没有，查询mysql
            // 设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("dress:lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                Example example = new Example(DressBaseinfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("occasionId",occasionId);
                dressBaseinfoList = dressBaseinfoMapper.selectByExample(example);

                if(dressBaseinfoList!=null){
                    // mysql查询结果存入redis
                    jedis.set("dress:"+occasionId+":infoList", JSON.toJSONString(dressBaseinfoList));
                }else{
                    // 数据库中不存在数据
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis
                    jedis.setex("dress:"+occasionId+":infoList",60*3,JSON.toJSONString(""));
                }

                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("dress:lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    jedis.del("dress:lock");// 用token确认删除的是自己的锁
                }

            }else{
                // 设置失败，自旋（重新尝试访问本方法）

                return getDressInfoByType(occasionId);
            }
        }

        jedis.close();
        return dressBaseinfoList;
    }

    @Override
    public DressBaseinfo getDressInfoById(String dressId) {
        Example example = new Example(DressImage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dressId",dressId);
        List<DressImage> dressImages = dressImageMapper.selectByExample(example);

        DressBaseinfo dressBaseinfo = dressBaseinfoMapper.selectByPrimaryKey(dressId);
        for(DressImage dressImage : dressImages){
            dressBaseinfo.setDressImage(dressImages);
        }

        int views = dressBaseinfo.getViews();
        setDressViews(dressId,views);

        deleteRedis(dressBaseinfo);

        return dressBaseinfo;
    }

    @Override
    public List<DressBaseinfo> selectDressById(String dressId) {
        Example example = new Example(DressBaseinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",dressId);
        return dressBaseinfoMapper.selectByExample(example);
    }

    @Override
    public List<DressBaseinfo> selectDressByName(String dressName) {
        Example example = new Example(DressBaseinfo.class);
        Example.Criteria criteria = example.createCriteria();
        String dName = "%" + dressName + "%";
        criteria.andLike("dressName",dName);
        return dressBaseinfoMapper.selectByExample(example);
    }

    public void  setDressViews(String dressId,int views){
        DressBaseinfo dressBaseinfo = new DressBaseinfo();
        dressBaseinfo.setId(dressId);
        dressBaseinfo.setViews(++views);
        dressBaseinfoMapper.updateByPrimaryKeySelective(dressBaseinfo);
    }

}
