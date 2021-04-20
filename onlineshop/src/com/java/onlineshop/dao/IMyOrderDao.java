package com.java.onlineshop.dao;

//MyOrderDao的接口



import com.java.onlineshop.pojo.MyOrder;

import java.util.List;

public interface IMyOrderDao
{
    //添加我的订单
    void addMyOrder(MyOrder myOrder);

    //更新我的订单
    void updateMyOrder(MyOrder myOrder);

    //删除我的订单：通过我的订单id删除
    void deleteMyOrder(int id);

    //返回当前数据库所有的MyOrder
    List<MyOrder> findAll();

    //通过用户名或id查找我的订单
    List<MyOrder> findByUserNameOrId(int userid);

    //根据订单id查找并返回订单
    MyOrder findById(int id);
}

