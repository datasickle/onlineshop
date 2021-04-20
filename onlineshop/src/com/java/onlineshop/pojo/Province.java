package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 省份实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:32
 **/
public class Province
{
    private Integer id;
    private String name;
    private Country country;//与country表建立联系

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }

    public Province(Integer id, String name, Country country)
    {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Province()
    {
    }
}
