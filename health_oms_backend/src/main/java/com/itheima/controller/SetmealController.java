package com.itheima.controller;

import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {


    @RequestMapping("/getToken")
    public Result getToken(){
        log.debug("获取token");
        String accessKey = "6LLhkymg55mZ2RiAMdloHAr-nYbhYgY2eAI2yOWb";
        String secretKey = "ZaqnW4nGn930xrYmMUYD-4-f02MSZK2kmAL-eJEa";
        String bucket = "hm-31";
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(bucket);
        log.debug("获取token成功：" + uploadToken);
        return new Result(true, MessageConst.GET_QINIU_TOKEN_SUCCESS, uploadToken);
    }
}
