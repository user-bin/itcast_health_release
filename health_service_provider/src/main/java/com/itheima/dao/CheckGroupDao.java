package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

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
	/**
	 * 基于条件分页获取检查组列表
	 * @param queryString
	 * @return
	 */
	Page<CheckGroup> findByCondition(@Param("queryString") String queryString);
}