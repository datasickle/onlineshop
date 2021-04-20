package com.java.onlineshop.test;

import com.java.onlineshop.impl.CategoryDaoImpl;
import com.java.onlineshop.pojo.Category;
import org.junit.jupiter.api.Test;

/**
 * @program: JDBCcourse
 * @description: CategoryDaoImpl的测试类
 * @author: ShuangChenYue
 * @create: 2021-04-18 15:44
 **/
public class TestCategoryDao
{
    private CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    @Test
    public void testFindById()
    {
        Category category = categoryDao.findById(2);
        System.out.println("———————————————————————————————————————");
        System.out.println("[categoryId]："+ category.getId());
        System.out.println("———————————————————————————————————————");
        System.out.println("[categoryName]："+category.getName());
        System.out.println("———————————————————————————————————————");
        System.out.println("[categoryDescription]："+category.getDescription());
        System.out.println("———————————————————————————————————————");
    }
}
