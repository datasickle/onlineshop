package com.java.onlineshop.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: JDBCcourse
 * @description: 我的订单实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:30
 **/
public class MyOrder
{
    private Integer orderID;
    private String name;
    private double cost;
    private User user;
    private PayWay payWay;
    private OrderStatus orderStatus;
    private Set orderlines = new HashSet();

    public Integer getOrderID()
    {
        return orderID;
    }

    public void setOrderID(Integer orderID)
    {
        this.orderID = orderID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public PayWay getPayWay()
    {
        return payWay;
    }

    public void setPayWay(PayWay payWay)
    {
        this.payWay = payWay;
    }

    public OrderStatus getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Set getOrderlines()
    {
        return orderlines;
    }

    public void setOrderlines(Set orderlines)
    {
        this.orderlines = orderlines;
    }

    public MyOrder(Integer orderID, String name, double cost, User user, PayWay payWay, OrderStatus orderStatus, Set orderlines)
    {
        this.orderID = orderID;
        this.name = name;
        this.cost = cost;
        this.user = user;
        this.payWay = payWay;
        this.orderStatus = orderStatus;
        this.orderlines = orderlines;
    }

    public MyOrder()
    {
    }

    public int getCount()
    {
        int count = 0;
        Iterator it = orderlines.iterator();
        while (it.hasNext())
        {
            OrderItem line = (OrderItem) it.next();
            count += line.getAmount();
        }
        return count;
    }

    @Override
    public String toString()
    {
        return "MyOrder{" +
                "orderID=" + orderID +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", user=" + user +
                ", payWay=" + payWay +
                ", orderStatus=" + orderStatus +
                ", orderlines=" + orderlines +
                '}';
    }
}
