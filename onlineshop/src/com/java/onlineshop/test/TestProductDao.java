package com.java.onlineshop.test;

import com.java.onlineshop.impl.ProductDaoImpl;
import com.java.onlineshop.pojo.Category;
import com.java.onlineshop.pojo.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @program: JDBCcourse
 * @description: ProductDaoImpl的测试类
 * @author: ShuangChenYue
 * @create: 2021-04-18 14:16
 **/
public class TestProductDao
{
    private ProductDaoImpl productDao = new ProductDaoImpl();
    @Test
    public void testAddProduct()
    {
        Product product = new Product();
        product.setName("Java程序设计");
        product.setPrice(45);
        Category category = new Category();
        category.setId(6);
        //注册和category的关系
        product.setCategory(category);
        productDao.addProduct(product);
    }

    @Test
    public void testUpdateProduct()
    {
        Product product = new Product();
        product.setName("曲奇");
        product.setPrice(5.5);
        Category category = new Category();
        category.setId(1);
        product.setCategory(category);
        product.setId(10);
        productDao.updateProduct(product);
    }

    @Test
    public void testFindById()
    {
        Product product = productDao.findById(3);
        System.out.println("———————————————————————————————————————");
        System.out.println("[productId]："+ product.getId());
        System.out.println("———————————————————————————————————————");
        System.out.println("[name]："+product.getName());
        System.out.println("———————————————————————————————————————");
        System.out.println("[price]："+product.getPrice());
        System.out.println("———————————————————————————————————————");
        System.out.println("[category]："+product.getCategory().getId());
        System.out.println("———————————————————————————————————————");
    }

    @Test
    public void testFindAll()
    {
        List<Product> list = productDao.findAll();
        for (int i=0;i<list.size();i++)
        {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void testByFindLikeName()
    {
        List<Product> list = productDao.findByLikeName("Java");
        for (int i=0;i<list.size();i++)
        {
            System.out.println(list.get(i));
        }
    }
}
