package com.java.onlineshop.test;

import com.java.onlineshop.dao.IUserDao;
import com.java.onlineshop.impl.UserDaoImpl;
import com.java.onlineshop.pojo.Country;
import com.java.onlineshop.pojo.Province;
import com.java.onlineshop.pojo.User;
import com.java.onlineshop.pojo.UserInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * @program: JDBCcourse
 * @description: 测试UserDao的实现
 * @author: ShuangChenYue
 * @create: 2021-04-08 13:39
 **/
public class TestUserDao
{
    private static IUserDao userDao = null;
    @BeforeAll
    public static void testInit()
    {
        userDao=new UserDaoImpl();
    }
//====================================================testAddUser=======================================================
    @Test
    public void testAddUser()
    {
        User user = new User();
        user.setName("ZhangSan");
        user.setPassword("200625");

        UserInfo info = new UserInfo();
        info.setStreet("SooChow");
        info.setCity("苏州");
        info.setEmail("ZhangSan@gmail.com");
        info.setPhone("12345678910");

        Country country = new Country();
        country.setId(1);

        Province province = new Province();
        province.setId(13);

        //注册关系
        user.setInfo(info);
        info.setUser(user);
        info.setCountry(country);
        info.setProvince(province);


        try
        {
            userDao.addUser(user);//需要传入一个user，我们实例化一个即可
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
//====================================================testFindUser=======================================================
    @Test
    public void testFindUser()
    {
        User user = userDao.findByNameAndPassword("ZhangSan","200625");
        System.out.println("====================user表中的信息===================");
        System.out.println("[userId]："+user.getId());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[userName]："+user.getName());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[userPassword]："+user.getPassword());
        System.out.println("==================userinfo表中的信息=================");
        System.out.println( "[userinfoID]："+user.getInfo().getId());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[street]："+user.getInfo().getStreet());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[city]："+user.getInfo().getCity());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[email]："+user.getInfo().getEmail());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[phone]："+user.getInfo().getPhone());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[countryId]："+user.getInfo().getCountry().getId());
        System.out.println("————————————————————————————————————————————————————");
        System.out.println("[provinceId]："+user.getInfo().getProvince().getId());
        System.out.println("————————————————————————————————————————————————————");
    }
//====================================================testFindAll=======================================================
    @Test
    public void testFindAll()
    {
        List<User> list = userDao.findAll();
        for(int i=0;i<list.size();i++)
        {
            System.out.println(list.get(i));
        }
    }
//====================================================testFindById=======================================================
    @Test
    public void testFindById()
    {
        User user = userDao.findById(6);
        System.out.println(user);
    }
}
