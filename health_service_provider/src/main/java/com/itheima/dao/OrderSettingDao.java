package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void edit(OrderSetting orderSetting);

    List<OrderSetting> findByMonth(String thisMonthFirstDay, String thisMonthLastDay);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
