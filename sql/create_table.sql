# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists xiaoguo;

-- 切换库
use xiaoguo;

# 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    planetCode   varchar(512)                       null comment '星球编号',
    tags         varchar(1024)                      null comment '标签 json 列表'
)
    comment '用户';

# 导入示例用户
INSERT INTO xiaoguo.user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode) VALUES ('luxiaoguo', 'xiaoguo', 'https://ts1.tc.mm.bing.net/th/id/R-C.77419dc707507ce56c7c702cc5213abb?rik=vk8Ee92g0blD3w&riu=http%3a%2f%2fpuui.qpic.cn%2fvpic_cover%2fg3104cnbxm1%2fg3104cnbxm1_hz.jpg%2f1280&ehk=cKGwr5NkA3vocJj75ytn77GmMI%2fHa1HySiAWmuCSGBc%3d&risl=&pid=ImgRaw&r=0', null, '0419ea58171ba436588b5b9758e28e1d', null, null, 0, '2025-10-16 14:14:22', '2025-10-16 14:39:37', 0, 1, '1');
# 加入列表
USE xiaoguo;
alter table `user`
    add column `tags` varchar(1024) null comment '标签列表';

# 队伍表

create table team
(
    id           bigint auto_increment comment 'id'
        primary key,
    name  varchar(256)                      not null comment '队伍名称',
    description    varchar(1024)                      null comment '队伍描述',
    maxNum        int                       default 1  null comment '最大人数',
    expiresTime   datetime                       null comment '过期时间',
    userId      bigint                              null comment '用户id（队长id）',
    status   int      default 0                 not null comment '用户状态 0 - 公开 1 - 私有 2 - 加密',
    password varchar(512)                       null comment '密码',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '队伍';
# 用户队伍关系表
create table user_team
(
    id           bigint auto_increment comment 'id'
        primary key,
    userId      bigint                              null comment '用户id',
    teamId      bigint                              null comment '队伍id',
    joinTime   datetime null comment '加入时间',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '用户队伍关系';