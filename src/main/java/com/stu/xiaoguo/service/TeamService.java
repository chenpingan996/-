package com.stu.xiaoguo.service;

import com.stu.xiaoguo.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.xiaoguo.model.domain.User;
import com.stu.xiaoguo.model.dto.TeamQuery;
import com.stu.xiaoguo.model.request.TeamJoinRequest;
import com.stu.xiaoguo.model.request.TeamQuitRequest;
import com.stu.xiaoguo.model.request.TeamUpdateRequest;
import com.stu.xiaoguo.model.vo.TeamUserVo;

import java.util.List;

/**
* @author penghe yang
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2025-11-05 15:48:23
*/
public interface TeamService extends IService<Team> {
    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @return
     */
    List<TeamUserVo> listTeams(TeamQuery teamQuery,boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @return
     */
    Boolean joinTeam(TeamJoinRequest teamJoinRequest,User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    Boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 解散/删除队伍
     * @param id
     * @return
     */
    boolean deleteTeam(long id,User loginUser);
}
