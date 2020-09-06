package com.itheima.dao;

import com.itheima.pojo.SysUser;

public interface UserDao {

	/**
	 * 基于名字，获取用户信息
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);
}