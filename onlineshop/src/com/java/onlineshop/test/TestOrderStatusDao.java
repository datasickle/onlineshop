package com.java.onlineshop.test;

import com.java.onlineshop.impl.OrderStatusDaoImpl;
import com.java.onlineshop.pojo.OrderStatus;
import org.junit.jupiter.api.Test;

/**
 * @program: JDBCcourse
 * @description: OrderStatusImpl的测试类
 * @author: ShuangChenYue
 * @create: 2021-04-19 21:34
 **/
public class TestOrderStatusDao
{
    private OrderStatusDaoImpl orderStatusDao = new OrderStatusDaoImpl();
    @Test
    public void testFindById()
    {
        OrderStatus orderStatus = orderStatusDao.findById(3);
        System.out.println(orderStatus);
    }
}
