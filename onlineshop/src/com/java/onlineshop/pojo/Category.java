package com.java.onlineshop.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: JDBCcourse
 * @description: 产品分类实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:31
 **/
public class Category
{
    private Integer id;
    private String name;
    private String description;
    private Set products = new HashSet();

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Set getProducts()
    {
        return products;
    }

    public void setProducts(Set products)
    {
        this.products = products;
    }

    public Category(Integer id, String name, String description, Set products)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public Category()
    {
    }

    @Override
    public String toString()
    {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", products=" + products +
                '}';
    }
}
