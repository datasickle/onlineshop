package com.java.onlineshop.impl;

import com.java.onlineshop.dao.ICategoryDao;
import com.java.onlineshop.pojo.Category;
import com.java.onlineshop.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @program: JDBCcourse
 * @description: ICategoryDao的实现类
 * @author: ShuangChenYue
 * @create: 2021-04-18 15:31
 **/
public class CategoryDaoImpl implements ICategoryDao
{

    @Override
    public Category findById(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "select * from category where id=?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setInt(1,id);
            //4.执行操作并返回结果集
            resultSet = preparedStatement.executeQuery();
            //5.处理结果集
            if (resultSet.next())
            {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
            }
            //手动提交
            connection.commit();
        }catch (Exception e)
        {
            try
            {
                connection.rollback();
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }finally
        {
            JdbcUtil.relase(resultSet,preparedStatement,connection);
        }
        return category;
    }
}
