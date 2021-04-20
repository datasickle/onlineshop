package com.java.onlineshop.dao;

import com.java.onlineshop.pojo.Category;

//CategoryDao的接口
public interface ICategoryDao
{
    Category findById(int id);
}
