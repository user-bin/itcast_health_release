package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.CheckItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Slf4j
public class CheckItemServiceImpl implements  CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        log.debug("service:{"+checkItem+"}");
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        log.debug("service:" + queryPageBean);
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 条件查询
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        log.debug("page{"+page+"}");
        //封装PageResult
        return new PageResult(page.getTotal(), page);
    }

    /**
     * 判断是否被检查组所关联
     *  1. 如果被关联，则不删除，抛出异常，不能删除
     *  2. 如果没有关联，直接删除检查项
     * @param id
     */
    @Override
    public void delById(Integer id) {
        long count = checkItemDao.findCountById(id);
        if(count > 0) {
            //说明被关联
            throw new BusinessRuntimeException("该检查项被检查组关联，不能删除！！");
        }else {
            checkItemDao.delById(id);
        }
    }

    /**
     * 获取某一数据
     * @param id 数据ID
     * @return 对象
     */
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }
}