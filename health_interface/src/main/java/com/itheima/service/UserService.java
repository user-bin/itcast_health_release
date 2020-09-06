package com.itheima.service;

import com.itheima.pojo.SysUser;

public interface UserService {
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密 码
	 * @return
	 */
	boolean login(String username,String password);

	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);

}