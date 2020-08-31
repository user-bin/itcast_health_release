package com.itheima.controller;

import com.itheima.entity.Result;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
public class UploadController {

    /**
     * 获取上传的凭证
     * @return
     */
    @RequestMapping("/getToken")
    public Result getToken(){
        System.out.println("开始生成上传凭证");
        String ak = "6LLhkymg55mZ2RiAMdloHAr-nYbhYgY2eAI2yOWb";
        String sk = "ZaqnW4nGn930xrYmMUYD-4-f02MSZK2kmAL-eJEa";
        //存储空间的名称
        String bucket = "hm-31";
        //创建认证对象
        Auth auth = Auth.create(ak, sk);
        //生成上传凭证
        String token = auth.uploadToken(bucket);

        return new Result(true, "获取上传凭证成功!!", token);
    }
}
