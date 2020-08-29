package com.itheima.dao;

import com.itheima.pojo.CheckGroup;

/**
 * 持久层Dao接口
 */
public interface CheckGroupDao {
	/**
	 * 添加检查组
	 * @param checkGroup
	 */
	void add(CheckGroup checkGroup);

	/**
	 * 添加检查组的检查项
	 */
	void set(Integer checkgroupId,Integer checkitemId);
    
}