package com.stu.xiaoguo.service;

import com.stu.xiaoguo.mapper.UserMapper;
import com.stu.xiaoguo.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class InsertUserTest {
    @Resource
    private UserService userService;
    //CPU密集型：分配的核心线程数=CPU-1
    //IO密集型：分配的核心线程数可以大于CPU核数
    private ExecutorService executorService = new ThreadPoolExecutor(60,1000,1000, TimeUnit.MINUTES,new ArrayBlockingQueue<>(1000));
    /**
     * 批量插入用户
     */
    @Test
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++){
            User user = new User();
            user.setUsername("假用户");
            user.setUserAccount("fakexiaoguo");
            user.setAvatarUrl("https://c-ssl.dtstatic.com/uploads/blog/202303/21/20230321223357_a5db3.thumb.1000_0.jpg");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("12345678910");
            user.setEmail("12345678@qq.com");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("11111");
            user.setTags("[]");
            userList.add(user);
        }
        userService.saveBatch(userList,100);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
    /**
     * 并发批量插入用户
     */
    @Test
    public void doConcurrencyInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 10000;
        //分十组
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            List<User> userList = new ArrayList<>();
            while(true){
                j++;
                User user = new User();
                user.setUsername("假用户");
                user.setUserAccount("fakexiaoguo");
                user.setAvatarUrl("https://c-ssl.dtstatic.com/uploads/blog/202303/21/20230321223357_a5db3.thumb.1000_0.jpg");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("12345678910");
                user.setEmail("12345678@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setPlanetCode("11111");
                user.setTags("[]");
                userList.add(user);
                if (j % 1000 == 0){
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, 1000);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
