package com.stu.xiaoguo.once;
import java.util.Date;

import com.stu.xiaoguo.mapper.UserMapper;
import com.stu.xiaoguo.model.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {
    @Resource
    private UserMapper userMapper;
    /**
     * 批量插入用户
     */
    @Scheduled(fixedDelay = 5000)
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 10000;
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
            //userMapper.insert(user);
        }
        stopWatch.stop();
        //System.out.println(stopWatch.getTotalTimeMillis());
    }
}
