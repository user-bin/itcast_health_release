package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Reference
	private UserService userService;

	@RequestMapping("/login")
	public Result login(String username, String password){
		System.out.println("oms backend,user:"+username+" ,password:"+password);
		if(userService.login(username,password)){
			System.out.println("login ok!!!");
			return new Result(true, MessageConst.ACTION_SUCCESS);
		}else{
			System.out.println("login fail");
			return new Result(false,MessageConst.ACTION_FAIL);
		}
	}
}