package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map<String,Object> findById(Integer id);

    /**
     * 今日的预约人数
     * @param todayDate
     * @return
     */
    long findTodayOrderNumber(String todayDate);

    /**
     * 今日到诊数
     * @param todayDate
     * @return
     */
    long findTodayVisitsNumber(String todayDate);

    /**
     * 查询一个日期区间的预约人数
     * @param startDate
     * @param endDate
     * @return
     */
    long findOrderNumberByBetweenDate(String startDate, String endDate);

    /**
     * 查询指定日期之后的实际到诊数
     * @param date
     * @return
     */
    long findVisitsNumberByAfterDate(String date);
}