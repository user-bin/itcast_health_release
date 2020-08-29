package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkgroup")
@Slf4j
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        log.debug("CheckGroupController:add : checkGroup:" +checkGroup);
        log.debug("CheckGroupController:add : checkitemIds: " +checkitemIds);
        checkGroupService.add(checkGroup,checkitemIds);
        log.debug("添加检查项成功");
        //新增成功
        return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
    }
}
