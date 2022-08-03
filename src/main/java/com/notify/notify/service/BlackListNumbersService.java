package com.notify.notify.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BlackListNumbersService {

    private SetOperations<String,String> blackList;
    private String KEY;

    public BlackListNumbersService(RedisTemplate <String,String> redisTemplate)
    {
        KEY="BlackListNumbers";
        blackList=redisTemplate.opsForSet();
    }

    public void add(String num)
    {
        blackList.add(KEY, num);
    }

    public Set<String> getAllNumbers()
    {
        return blackList.members(KEY);
    }

    public Boolean isExist(String num)
    {
        return blackList.isMember(KEY,num);
    }

    public void remove(String num)
    {
        blackList.remove(KEY,num);
    }
}
