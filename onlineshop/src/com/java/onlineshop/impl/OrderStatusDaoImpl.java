package com.java.onlineshop.impl;

import com.java.onlineshop.dao.IOrderStatusDao;
import com.java.onlineshop.pojo.OrderStatus;
import com.java.onlineshop.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: JDBCcourse
 * @description: OrderStatusDao的实现类
 * @author: ShuangChenYue
 * @create: 2021-04-19 21:29
 **/
public class OrderStatusDaoImpl implements IOrderStatusDao
{

    @Override
    public OrderStatus findById(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OrderStatus orderStatus = null;
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from orderstatus where orderStatusId=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                orderStatus = new OrderStatus();
                orderStatus.setOrderStatusId(resultSet.getInt("orderStatusId"));
                orderStatus.setName(resultSet.getString("name"));
                orderStatus.setDescription(resultSet.getString("description"));
            }
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
        }finally
        {
            JdbcUtil.relase(resultSet,preparedStatement,connection);
        }
        return orderStatus;
    }
}
