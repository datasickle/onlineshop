package com.java.onlineshop.test;

import com.java.onlineshop.dao.IMyOrderDao;
import com.java.onlineshop.dao.IProductDao;
import com.java.onlineshop.impl.MyOrderDaoImpl;
import com.java.onlineshop.impl.ProductDaoImpl;
import com.java.onlineshop.pojo.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @program: JDBCcourse
 * @description: MyOrderDaoImpl的测试类
 * @author: ShuangChenYue
 * @create: 2021-04-18 22:00
 **/
public class TestMyOrderDao
{
    private static IMyOrderDao orderDao = null;
    private  static IProductDao productDao = null;
    @BeforeAll
    public static void testInit(){
        orderDao = new MyOrderDaoImpl();
        productDao = new ProductDaoImpl();
    }
    @Test
    public void testAddMyOrder()
    {
        User user = new User();
        user.setId(6);
        user.setName("ZhangSan");

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatusId(3);
        orderStatus.setName("交易途中");
        orderStatus.setDescription("订单正在送货途中");

        PayWay payWay = new PayWay();
        payWay.setId(2);
        payWay.setPayStyle("网银付款");

        MyOrder myOrder = new MyOrder();
        myOrder.setUser(user);
        myOrder.setName(myOrder.getUser().getName());

        Product product1 = productDao.findById(1);
        Product product2 = productDao.findById(11);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setAmount(1);
        orderItem1.setProduct(product1);
        orderItem1.setMyOrder(myOrder);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setAmount(2);
        orderItem2.setProduct(product2);
        orderItem2.setMyOrder(myOrder);

        myOrder.setCost(orderItem1.getAmount()*product1.getPrice()+orderItem2.getAmount()*product2.getPrice());

        //注册关系
        myOrder.setPayWay(payWay);
        myOrder.setOrderStatus(orderStatus);

        myOrder.getOrderlines().add(orderItem1);
        myOrder.getOrderlines().add(orderItem2);

        orderDao.addMyOrder(myOrder);
    }

    @Test
    public void testUpdateMyOrder()
    {
        MyOrder myOrder = orderDao.findById(7);
        System.out.println(myOrder.getCost());
        User user = myOrder.getUser();
        System.out.println(user.getName()+":"+user.getId());
        System.out.println(user.getInfo().getStreet());
        myOrder.getUser().getInfo().setStreet("华尔街");
        myOrder.getUser().getInfo().setCity("NewYork");
        orderDao.updateMyOrder(myOrder);
    }

    @Test
    public void testDeleteById()
    {
        orderDao.deleteMyOrder(7);
    }

    @Test
    public void testFindById()
    {
        MyOrder myOrder = orderDao.findById(6);
        System.out.println(myOrder);
    }

    @Test
    public void testFindAll()
    {
        List<MyOrder> myOrderList = orderDao.findAll();
        for (int i=0;i<myOrderList.size();i++)
        {
            System.out.println(myOrderList.get(i));
        }
    }

    @Test
    public void testFindByUserNameOrId()
    {
        List<MyOrder> myOrderList = orderDao.findByUserNameOrId(6);
        for (int i=0;i<myOrderList.size();i++)
        {
            System.out.println(myOrderList.get(i));
        }
    }
}
