package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 订单状态实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:31
 **/
public class OrderStatus
{
    private Integer orderStatusId;
    private String name;
    private String description;

    public Integer getOrderStatusId()
    {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId)
    {
        this.orderStatusId = orderStatusId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public OrderStatus(Integer orderStatusId, String name, String description)
    {
        this.orderStatusId = orderStatusId;
        this.name = name;
        this.description = description;
    }

    public OrderStatus()
    {
    }

    @Override
    public String toString()
    {
        return "OrderStatus{" +
                "orderStatusId=" + orderStatusId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
