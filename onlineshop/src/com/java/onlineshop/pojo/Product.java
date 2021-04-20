package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 产品实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:31
 **/
public class Product
{
    private Integer id;
    private String name;
    private double price;
    private Category category;

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

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Product(Integer id, String name, double price, Category category)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product()
    {
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
