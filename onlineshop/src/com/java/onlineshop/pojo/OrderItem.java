package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 订单详情实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:31
 **/
public class OrderItem
{
    private Integer orderitemId;
    private int amount;
    private Product product;
    private MyOrder myOrder;

    public Integer getOrderitemId()
    {
        return orderitemId;
    }

    public void setOrderitemId(Integer orderitemId)
    {
        this.orderitemId = orderitemId;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public MyOrder getMyOrder()
    {
        return myOrder;
    }

    public void setMyOrder(MyOrder myOrder)
    {
        this.myOrder = myOrder;
    }

    public OrderItem(Integer orderitemId, int amount, Product product, MyOrder myOrder)
    {
        this.orderitemId = orderitemId;
        this.amount = amount;
        this.product = product;
        this.myOrder = myOrder;
    }

    public OrderItem()
    {
    }
}
