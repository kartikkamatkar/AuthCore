package com.Auth.Authcore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService
{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public void blacklistToken(String token){
        redisTemplate.opsForValue().set(token,"BLACKLISTED",1, TimeUnit.HOURS);
    }
    public boolean isBlacklisted(String token){
        return  redisTemplate.hasKey(token);
    }
    public void saveOtp (String email,String otp)
    {
        redisTemplate.opsForValue().set(email,otp,5,TimeUnit.MINUTES);
    }
    public String getOTP(String email){
        return (String) redisTemplate.opsForValue().get(email);
    }

}