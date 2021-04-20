package com.java.onlineshop.dao;

import com.java.onlineshop.pojo.Country;

//此接口只有一个根据id查找国家的方法，返回一个Country类型的值
public interface ICountryDao
{
    Country findById(int id);
}
