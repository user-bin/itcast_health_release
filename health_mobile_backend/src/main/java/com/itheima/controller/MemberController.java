package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 会员管理
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Reference
    private MemberService memberService;

    @Autowired
    JedisPool jedisPool;

    @Reference
    ValidateCodeService validateCodeService;

    //手机验证码登录
    @PostMapping("/login")
    public Result login(HttpServletResponse response,@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        log.debug("MemberController-login: param { " + map.toString() + " }");
        //1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        if(!validateCodeService.checkValidateCode(telephone, RedisMessageConstant.SENDTYPE_LOGIN, validateCode)){
            //验证码错误
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }

        //2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
        Member member = memberService.findByTelephone(telephone);
        if(member == null){
            //不是会员，需要自动注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }

        //3、向客户端写入Cookie，内容为用户手机号
        Cookie cookie = new Cookie("member_login_telephone",telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        //将Cookie写入到客户端浏览器
        response.addCookie(cookie);

        //4、将会员信息保存到Redis，使用手机号作为key，保存时长为30分钟
        Jedis jedis = jedisPool.getResource();
        jedis.setex(telephone,60 * 30, JSON.toJSON(member).toString());
        log.debug(MessageConst.LOGIN_SUCCESS);
        return new Result(true,MessageConst.LOGIN_SUCCESS);
    }
}
