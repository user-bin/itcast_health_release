package com.itheima.service;

import com.itheima.pojo.CheckGroup;

/**
 * 检查组服务接口
 */
public interface CheckGroupService {
	/**
	 * 添加检查组
	 * @param checkGroup 检查组基本信息
	 * @param checkItemIds 检查项ID列表
	 */
	public void add(CheckGroup checkGroup, Integer[] checkItemIds);
}