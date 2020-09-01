package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     * 添加多个orderSetting 对象
     * @param orderSettingList
     */
    @Override
    public void addOrderSettingList(List<OrderSetting> orderSettingList) {
        if(orderSettingList != null && orderSettingList.size() > 0){
            for (OrderSetting orderSetting : orderSettingList) {
                saveOrEdit(orderSetting);
            }
        }
    }

    /**
     * 添加或者修改orderSetting对象
     * 步骤：
     *  1. 根据日期查询预约设置对象
     *  2. 如果能查到预约设置对象，修改
     *      如果已预约大于可预约， 不能设置, 抛出异常
     *  3. 如果查不到预约设置对象，添加
     *
     * @param orderSetting 从excel中读取出来的
     */
    public void saveOrEdit(OrderSetting orderSetting){
        //根据日期从数据库查询预约设置对象
        OrderSetting orderSettingDb  = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //如果能查到预约设置对象，修改
        if(orderSettingDb != null){
            //如果已预约大于可预约， 不能设置, 抛出异常
            if(orderSettingDb.getReservations() > orderSetting.getNumber()){
                throw new BusinessRuntimeException("已预约大于可预约，不能设置!!");
            }
            orderSettingDao.edit(orderSetting);
        }else{
            //如果查不到预约设置对象，添加
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<OrderSetting> findByMonth(String date) {
        //2020-02-01
        String thisMonthFirstDay = date + "-01";
        Date thisMonthLastDayDate = DateUtils.getThisMonthLastDay(thisMonthFirstDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String thisMonthLastDay = sdf.format(thisMonthLastDayDate);
        return orderSettingDao.findByMonth(thisMonthFirstDay,thisMonthLastDay );

    }
}
