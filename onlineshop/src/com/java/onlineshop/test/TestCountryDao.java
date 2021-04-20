package com.java.onlineshop.test;

import com.java.onlineshop.impl.CountryDaoImpl;
import com.java.onlineshop.pojo.Country;
import org.junit.jupiter.api.Test;

/**
 * @program: JDBCcourse
 * @description: CountryDaoImpl的测试类
 * @author: ShuangChenYue
 * @create: 2021-04-19 20:25
 **/
public class TestCountryDao
{
    private CountryDaoImpl countryDao = new CountryDaoImpl();

    @Test
    public void testFindById()
    {
        Country country = countryDao.findById(1);
        System.out.println(country);
    }

}
