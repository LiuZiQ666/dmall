package com.gdou.dmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gdou.dmall.bean.NewsInfo;
import com.gdou.dmall.manage.mapper.NewsInfoMapper;
import com.gdou.dmall.service.NewsService;
import com.gdou.dmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;


@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsInfoMapper newsInfoMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<NewsInfo> getNewsInfo() {
        return newsInfoMapper.selectAll();
    }

    @Override
    public void saveNewsInfo(NewsInfo newsInfo) {
        newsInfoMapper.insertSelective(newsInfo);
        Jedis jedis = redisUtil.getJedis();
        String newsKey = "news:infoList";
        jedis.del(newsKey);
        jedis.close();
    }

    @Override
    public void deleteNewsInfo(NewsInfo newsInfo) {

        newsInfoMapper.delete(newsInfo);
        Jedis jedis = redisUtil.getJedis();
        String newsKey1 = "news:infoList";
        jedis.del(newsKey1);
        String newsId = newsInfo.getId();
        String newsKey2 = "news:"+newsId+":info";
        jedis.del(newsKey2);
        jedis.close();
    }

    @Override
    public List<NewsInfo> getNewsInfoByRedis() {

        List<NewsInfo> newsInfoList;

        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String newsKey = "news:infoList";
        String newsJson = jedis.get(newsKey);

        if(StringUtils.isNotBlank(newsJson)){
            newsInfoList = JSON.parseObject(newsJson,new TypeReference<List<NewsInfo>>(){} );
        }else{
            // 如果缓存中没有，查询mysql
            // 设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("news:lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                newsInfoList =  getNewsInfo();

                if(newsInfoList!=null){
                    // mysql查询结果存入redis
                    jedis.set("news:infoList", JSON.toJSONString(newsInfoList));
                }else{
                    // 数据库中不存在数据
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis
                    jedis.setex("news:infoList",60*3,JSON.toJSONString(""));
                }

                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("news:lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    jedis.del("news:lock");// 用token确认删除的是自己的锁
                }

            }else{
                // 设置失败，自旋（重新尝试访问本方法）

                return getNewsInfoByRedis();
            }
        }

        jedis.close();
        return newsInfoList;
    }

    @Override
    public NewsInfo getNewsItemByRedis(String newsId) {
       // NewsInfo newsInfo = newsInfoMapper.selectByPrimaryKey(newsId);
        NewsInfo newsInfo = new NewsInfo();

        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String newsKey = "news:"+newsId+":info";
        String newsJson = jedis.get(newsKey);

        if(StringUtils.isNotBlank(newsJson)){
            newsInfo = JSON.parseObject(newsJson,NewsInfo.class);
        }else{
            // 如果缓存中没有，查询mysql
            // 设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("news:"+newsId+"lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                newsInfo = newsInfoMapper.selectByPrimaryKey(newsId);

                if(newsInfo!=null){
                    // mysql查询结果存入redis
                    jedis.set("news:"+newsId+":info", JSON.toJSONString(newsInfo));
                }else{
                    // 数据库中不存在数据
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis
                    jedis.setex("news:"+newsId+":info",60*3,JSON.toJSONString(""));
                }

                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("news:"+newsId+"lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    jedis.del("news:"+newsId+"lock");// 用token确认删除的是自己的锁
                }

            }else{
                // 设置失败，自旋（重新尝试访问本方法）

                return getNewsItemByRedis(newsId);
            }
        }

        jedis.close();
        return newsInfo;
    }

    @Override
    public List<NewsInfo> selectNewsByName(String newsName) {
        Example example = new Example(NewsInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String dName = "%" + newsName + "%";
        criteria.andLike("title",dName);
        return newsInfoMapper.selectByExample(example);
    }


}
