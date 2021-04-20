package com.java.onlineshop.impl;

import com.java.onlineshop.dao.ICountryDao;
import com.java.onlineshop.pojo.Country;
import com.java.onlineshop.util.BasicDao;

/**
 * @program: JDBCcourse
 * @description: ICountryDao的实现类
 * @author: ShuangChenYue
 * @create: 2021-04-12 22:47
 **/
public class CountryDaoImpl implements ICountryDao
{

    @Override
    public Country findById(int id)
    {
        return BasicDao.getObject(Country.class,"select id as id,name from country where id=?",id);
    }
}
