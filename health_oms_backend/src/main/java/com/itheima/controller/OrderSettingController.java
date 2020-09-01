package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/ordersetting")
@Slf4j
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> strsList = POIUtils.readExcel(excelFile);
            //把string数组 集合对象 转换为 OrderSetting集合对象
            List<OrderSetting> orderSettingList = new ArrayList<>();
            // 循环string数组 集合对象
            for (String[] strings : strsList) {
                //一个string数组对应一个OrderSetting
                OrderSetting orderSetting = new OrderSetting();
                //把字符串日期转换为 日期类型
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date orderDate = sdf.parse(strings[0]);
                orderSetting.setOrderDate(orderDate);
                //把 字符串转换为 整数
                orderSetting.setNumber(Integer.parseInt(strings[1]));
                //把OrderSetting对象添加到集合中
                orderSettingList.add(orderSetting);
            }
            // 调用service 存储到数据库
            orderSettingService.addOrderSettingList(orderSettingList);

        } catch (BusinessRuntimeException e ) {
            e.printStackTrace();
            throw new BusinessRuntimeException(e.getMessage());
        } catch (Exception e ) {
            e.printStackTrace();
            throw new BusinessRuntimeException(MessageConst.IMPORT_ORDERSETTING_FAIL);
        }

        return new Result(true,MessageConst.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * //获取到的json
     * [
     *  {id:1 , orderDate: 2020-02-26, reservations : 1 , number: 500},
     *  {id:2 , orderDate: 2020-02-27, reservations : 1 , number: 500},
     *  {id:3 , orderDate: 2020-02-28, reservations : 1 , number: 500},
     * ]
     *
     * 前端需要
     *  [
     { date: 21, number: 120, reservations: 1 },
     { date: 13, number: 120, reservations: 1 },
     { date: 24, number: 120, reservations: 120 }
     ];
     *
     * @param date
     * @return
     */
    @RequestMapping("/findByMonth")
    public Result findByMonth(String date){
        log.debug("OrderSettingController: findByDate: " + date);
        List<OrderSetting> orderSettingList = orderSettingService.findByMonth(date);
        // 需要把数据转换为前端想要的数据
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            //每一个orderSetting 对应一个Map集合
            Map<String,Object> map = new HashMap<>();
            //
            Date orderDate = orderSetting.getOrderDate();
            //把日期类型 获取里面的天
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            String orderDateStr = sdf.format(orderDate);
            map.put("date", orderDateStr);
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            //把map添加到集合中
            mapList.add(map);
        }
        log.debug(orderSettingList.toString());
        return new Result(true,MessageConst.GET_ORDERSETTING_SUCCESS , mapList);
    }

    @RequestMapping("/saveOrEdit")
    public Result saveOrEdit(@RequestBody OrderSetting orderSetting){
        log.debug("OrderSettingController: saveOrEdit:" + orderSetting);
        orderSettingService.saveOrEdit(orderSetting);
        log.debug("基于日历修改预约设置信息成功！！！");
        return new Result(true,"基于日历修改预约设置信息成功！！");
    }
}
