package com.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	/**
	 * 登录成功后访问的方法
	 * 	登录成功以后： pages/main.html
	 */
	@RequestMapping("/loginSuccess")
	public ModelAndView loginSuccess(){
		log.debug("登录成功!!");
		//在ModelAndView 中指定视图名称
		//默认为请求转发
		// 设置为重定向
		return new ModelAndView("redirect:http://localhost:83/pages/main.html");
	}

	/**
	 * 登录失败后访问的方法
	 * 登录失败以后：跳转到登录页面, 重新登录, 提示登录失败
	 */
	@RequestMapping("/loginFail")
	public ModelAndView loginFail(){
		log.debug("登录失败!!");
		return new ModelAndView("redirect:http://localhost:83/login.html");
	}
}