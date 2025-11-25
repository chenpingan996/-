package com.stu.xiaoguo.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedissonTest {
    @Resource
    private RedissonClient redissonClient;
    @Test
    void test() {
        //list
        //数据存在Redis的内存中
        RList<String> rList = redissonClient.getList("test-list");
        //rList.add("xiaoguo");
        //System.out.println(rList.get(0));
        rList.remove(0);
        //map
        RMap<String, Integer> map1 = redissonClient.getMap("test-map");
        //set

        //stack

    }
}
