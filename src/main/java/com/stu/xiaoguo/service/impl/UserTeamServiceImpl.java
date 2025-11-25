package com.stu.xiaoguo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.xiaoguo.model.domain.UserTeam;
import com.stu.xiaoguo.service.UserTeamService;
import com.stu.xiaoguo.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author penghe yang
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2025-11-05 15:50:58
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




