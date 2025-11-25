package com.stu.xiaoguo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 */
@Data
public class PageRequest implements Serializable {
    private static final long serialVersionUID = -4285125125125125125L;
    /**
     * 页面大小
     */
    protected int pageSize = 10;
    /**
     * 当前是第几页
     */
    protected int pageNum = 1;
}
