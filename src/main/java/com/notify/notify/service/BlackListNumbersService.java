package com.notify.notify.service;

import com.notify.notify.constants.redis.RedisConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BlackListNumbersService {

    private SetOperations<String, String> blackList;
    private String key;

    public BlackListNumbersService(RedisTemplate<String, String> redisTemplate) {
        key = RedisConstants.REDIS_KEY_FOR_BLACKLISTED_NUMBERS_SET;
        blackList = redisTemplate.opsForSet();
    }

    public void add(String phoneNumber) {
        blackList.add(key, phoneNumber);
    }

    public Set<String> getAllNumbers() {
        return blackList.members(key);
    }

    public Boolean isExist(String phoneNumber) {
        return blackList.isMember(key, phoneNumber);
    }

    public void remove(String phoneNumber) {
        blackList.remove(key, phoneNumber);
    }
}