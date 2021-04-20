package com.java.onlineshop.dao;



import com.java.onlineshop.pojo.PayWay;

import java.util.List;

//PayWay的接口
public interface IPayWayDao
{
    //添加支付方式
    void addPayWay(PayWay payWay);

    //更新支付方式
    void updatePayWay(PayWay payWay);

    //返回当前数据库
    List<PayWay> findAll();

    //通过id查找支付方式
    PayWay findById(int id);
}
