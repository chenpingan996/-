package com.stu.xiaoguo.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.xiaoguo.mapper.UserMapper;
import com.stu.xiaoguo.model.domain.User;
import com.stu.xiaoguo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热
 *
 * @author xiaoguo
 */
@Component
@Slf4j
public class ProCacheJob {
//    @Resource
//    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    //重点用户 单独缓存
    private List<Long> mainUserList = Arrays.asList(1L);

    //每天执行 预热推荐用户
    @Scheduled(cron = "0 30 16 * * ?")
    public void doCacheRecommendUser(){
        RLock lock = redissonClient.getLock("xiaoguo:procachejob:docache:lock");
        try {
            //只有一个线程能获取到锁
            if (lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)){
                System.out.println("get lock"+Thread.currentThread().getId());
                for (Long userId : mainUserList) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1,20),queryWrapper);
                    String redisKey = String.format("xiaoguo:user:recommend:%s", userId);
                    ValueOperations<String,Object> valueOperations =  redisTemplate.opsForValue();

                    //写缓存
                    try {
                        valueOperations.set(redisKey,userPage,10000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error",e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendUser error",e);
        } finally {
            //只能释放自己的锁
            if (lock.isHeldByCurrentThread()){
                System.out.println("unlock"+Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}
