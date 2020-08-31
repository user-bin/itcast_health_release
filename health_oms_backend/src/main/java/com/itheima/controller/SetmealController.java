package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {


    @Reference
    SetmealService setmealService;

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

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        log.debug("SetmealControlller:add:" + setmeal);
        log.debug("SetmealControlller:add:" + Arrays.toString(checkgroupIds));
        setmealService.add(setmeal, checkgroupIds);
        log.debug("套餐添加成功！！");
        return  new Result(true ,MessageConst.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页获取套餐数据
     * @param queryPageBean 查询参数
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("SetmealControlller:findPage:" + queryPageBean);
        PageResult pageResult = setmealService.findPage(queryPageBean);
        log.debug("PageResult:" + pageResult);
        return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS, pageResult);
    }
}
