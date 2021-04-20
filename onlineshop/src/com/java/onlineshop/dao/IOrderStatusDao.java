package com.java.onlineshop.dao;

//OrderStatus的接口

import com.java.onlineshop.pojo.OrderStatus;

public interface IOrderStatusDao
{
    OrderStatus findById(int id);
}
