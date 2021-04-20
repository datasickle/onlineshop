package com.java.onlineshop.dao;

//User的接口



import com.java.onlineshop.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao
{
    //添加用户
    void addUser(User user) throws SQLException, ClassNotFoundException;

    //修改用户
    void updateUser(User user);

    //查询用户：根据姓名和密码查询，返回User
    User findByNameAndPassword(String name,String password);

    //模糊查询：返回数据库的所有用户
    List<User> findAll();

    //根据用户id查找
    User findById(int id);
}
