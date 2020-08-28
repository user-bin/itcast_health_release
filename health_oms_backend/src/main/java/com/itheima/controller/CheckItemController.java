package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
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
@RequestMapping("/checkitem")
@Slf4j
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        log.debug("controller:{"+checkItem+"}");
        checkItemService.add(checkItem);
        log.debug("检查项添加成功！！");
        return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     *  1.调用Service获取分页结果数据
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("controller{"+queryPageBean+"}");
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        log.debug("检查项分页查询成功!!");
        return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, pageResult);
    }
}