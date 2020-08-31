package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;

    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1. 添加套餐（回显主键）
        setmealDao.add(setmeal);
        //2. 维护套餐和检查组的关系
        setRelation(setmeal, checkgroupIds);
    }

    /**
     * 设置检查组和套餐的关系
     * @param setmeal
     * @param checkgroupIds
     */
    private void setRelation(Setmeal setmeal,Integer[] checkgroupIds){
        if(setmeal.getId()!= null && checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setRelation(setmeal.getId(), checkgroupId);
            }
        }
    }
}
