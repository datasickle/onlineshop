package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 支付方式实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:32
 **/
public class PayWay
{
    private Integer id;
    private String payStyle;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPayStyle()
    {
        return payStyle;
    }

    public void setPayStyle(String payStyle)
    {
        this.payStyle = payStyle;
    }

    public PayWay(Integer id, String payStyle)
    {
        this.id = id;
        this.payStyle = payStyle;
    }

    public PayWay()
    {
    }

    @Override
    public String toString()
    {
        return "PayWay{" +
                "id=" + id +
                ", payStyle='" + payStyle + '\'' +
                '}';
    }
}
