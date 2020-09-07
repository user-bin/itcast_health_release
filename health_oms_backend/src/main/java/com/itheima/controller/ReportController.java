package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

    /**
     * {
     *     months: []
     *      memberCount:[]
     * }
     *
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = memberService.getMemberReport();
            return new Result(true, MessageConst.GET_MEMBER_NUMBER_REPORT_SUCCESS,map );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.GET_MEMBER_NUMBER_REPORT_FAIL );
        }
    }

    @Reference
    private SetmealService setmealService;
    /**
     * 套餐占比统计
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try{
            // 获取套餐占比数据
            List<Map<String,Object>> setmealCountList = setmealService.findSetmealCount();
            // 获取套餐名称
            List<String> setmealNames = new ArrayList<>();
            for (Map<String,Object> mapSetmeal:setmealCountList){
                String setmealName = (String)mapSetmeal.get("name");
                setmealNames.add(setmealName);
            }
            // 构建返回数据
            Map<String,Object> mapData = new HashMap<>();
            mapData.put("setmealCount",setmealCountList);
            mapData.put("setmealNames",setmealNames);
            return new Result(true,MessageConst.ACTION_SUCCESS,mapData);

        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.ACTION_FAIL);
        }
    }
}
