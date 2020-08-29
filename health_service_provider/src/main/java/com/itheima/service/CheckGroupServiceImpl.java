package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.CheckGroupDao;
import com.itheima.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 检查组服务
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {

	@Autowired
	private CheckGroupDao checkGroupDao;

	/**
     * 1. 添加检查组对象， 回显主键id
     * 2. 维护中间表的数据（检查组和检查项的关系）
     *
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup , Integer[] checkitemIds) {
        //1. 添加检查组对象， 回显主键id
        checkGroupDao.add(checkGroup);
        //2. 维护中间表的数据（检查组和检查项的关系）
        if(checkGroup.getId() != null){
            setRelation(checkGroup.getId(), checkitemIds);
        }
    }

    /**
     * 维护检查组和检查项的关系
     * @param checkgroupId
     * @param checkItemId
     */
    public void setRelation(Integer checkgroupId ,Integer[] checkItemId){
        if(checkgroupId != null && checkItemId != null && checkItemId.length > 0){
            for (Integer checkitemId : checkItemId) {
                checkGroupDao.set(checkgroupId ,checkitemId );
            }
        }
    }
}