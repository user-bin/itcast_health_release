package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

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

    /**
     * 基于ID，删除检查项
     * @param id
     */
    void delById(Integer id);
    /**
     * 基于ID，获取数据
     * @param id ID
     * @return 对象
     */
    public CheckItem findById(Integer id);

    /**
     * 更新检查项
     * @param checkItem
     */
    public void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}