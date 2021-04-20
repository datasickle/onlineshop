package com.java.onlineshop.test;

import com.java.onlineshop.impl.PayWayDaoImpl;
import com.java.onlineshop.pojo.PayWay;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @program: JDBCcourse
 * @description: PayWayDaoImpl的测试类
 * @author: ShuangChenYue
 * @create: 2021-04-19 20:10
 **/
public class TestPayWayDao
{
    private PayWayDaoImpl payWayDao = new PayWayDaoImpl();
    @Test
    public void testAddPayWay()
    {
        PayWay payWay = new PayWay();
        payWay.setPayStyle("冥币付款");
        payWayDao.addPayWay(payWay);
    }

    @Test
    public void testUpdatePayWay()
    {
        PayWay payWay = new PayWay();
        payWay.setPayStyle("假币付款");
        payWay.setId(4);
        payWayDao.updatePayWay(payWay);
    }

    @Test
    public void findAll()
    {
        List<PayWay> paywayList = payWayDao.findAll();
        for (int i=0;i<paywayList.size();i++)
        {
            System.out.println(paywayList.get(i));
        }
    }

    @Test
    public void testFindById()
    {
        PayWay payWay = payWayDao.findById(2);
        System.out.println(payWay);
    }
}
