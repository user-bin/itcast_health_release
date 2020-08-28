package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

/**
 * 检查项服务接口
 */
public interface CheckItemService {
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean 分页参数
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);
}