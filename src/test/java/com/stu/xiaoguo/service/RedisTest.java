package com.stu.xiaoguo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
    @Test
    void test(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //增
        valueOperations.set("xiaoguoString","绿苹果");
        valueOperations.set("xiaoguoInt",1);
        valueOperations.set("xiaoguoDouble",2.0);
        //查
        Object xiaoguoString = valueOperations.get("xiaoguoString");
        Assertions.assertTrue("绿苹果".equals((String) xiaoguoString));
        xiaoguoString = valueOperations.get("xiaoguoInt");
        Assertions.assertTrue(1 == (Integer) xiaoguoString);
        xiaoguoString = valueOperations.get("xiaoguoDouble");
        Assertions.assertTrue(2.0 == (Double) xiaoguoString);
        //改
        valueOperations.set("xiaoguoString","小果");
        //删
        redisTemplate.delete("xiaoguoString");

    }
}
