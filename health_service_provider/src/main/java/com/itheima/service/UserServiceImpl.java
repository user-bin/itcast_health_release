package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.exception.BusinessRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Override
	public boolean login(String username, String password) {
		log.debug("service_provide...u:"+username+" p:"+password);
		if("admin".equals(username) && "123".equals(password)){
			return true;
		}else{
			throw new BusinessRuntimeException("登录失败!!!");
		}
	}
}