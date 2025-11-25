package com.stu.xiaoguo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户加入队伍请求
 */
@Data
public class TeamJoinRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private Long teamId;
    private String password;
}
