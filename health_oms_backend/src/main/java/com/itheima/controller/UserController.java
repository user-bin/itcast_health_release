package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Reference
	private UserService userService;

	@RequestMapping("/login")
	public Result login(String username, String password){
		log.debug("oms backend,user:"+username+" ,password:"+password);
		if(userService.login(username,password)){
			log.debug("login ok!!!");
			return new Result(true, MessageConst.ACTION_SUCCESS);
		}else{
			log.debug("login fail");
			return new Result(false,MessageConst.ACTION_FAIL);
		}
	}
}