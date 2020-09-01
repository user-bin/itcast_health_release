package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
