package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 国家实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:32
 **/
public class Country
{
    private Integer id;//国家id
    private String name;//国家名称

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

    public Country(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Country()
    {
        super();
    }
    public Country(String name)
    {
        super();
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
