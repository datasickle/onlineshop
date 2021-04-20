package com.java.onlineshop.dao;



import com.java.onlineshop.pojo.Product;

import java.util.List;

//Product的接口
public interface IProductDao
{
    //添加产品
    void addProduct(Product product);

    //更新产品
    void updateProduct(Product product);

    //根据id查产品，并返回product
    Product findById(int id);

    //返回当前数据库的所有产品
    List<Product> findAll();

    //模糊查询
    List<Product> findByLikeName(String name);

}
