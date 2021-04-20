package com.java.onlineshop.impl;

import com.java.onlineshop.dao.IPayWayDao;
import com.java.onlineshop.pojo.PayWay;
import com.java.onlineshop.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBCcourse
 * @description: PayWayDao的实现类
 * @author: ShuangChenYue
 * @create: 2021-04-19 20:06
 **/
public class PayWayDaoImpl implements IPayWayDao
{

    @Override
    public void addPayWay(PayWay payWay)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into payway(payStyle)values (?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,payWay.getPayStyle());
            int i = preparedStatement.executeUpdate();
            System.out.println(i+"条记录被影响");
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
            JdbcUtil.relase(null,preparedStatement,connection);
        }
    }

    @Override
    public void updatePayWay(PayWay payWay)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update payway set payStyle=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,payWay.getPayStyle());
            preparedStatement.setInt(2,payWay.getId());
            int i = preparedStatement.executeUpdate();
            System.out.println(i+"条记录被影响");
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
            JdbcUtil.relase(null,preparedStatement,connection);
        }
    }

    @Override
    public List<PayWay> findAll()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PayWay payWay = null;
        List<PayWay> payWayList = new ArrayList<PayWay>();
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from payway";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                payWay = new PayWay();
                payWay.setId(resultSet.getInt("id"));
                payWay.setPayStyle(resultSet.getString("payStyle"));
                payWayList.add(payWay);
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
            e.printStackTrace();
        }finally
        {
            JdbcUtil.relase(resultSet,preparedStatement,connection);
        }
        return payWayList;
    }

    @Override
    public PayWay findById(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PayWay payWay = null;
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from payway where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                payWay = new PayWay();
                payWay.setId(resultSet.getInt("id"));
                payWay.setPayStyle(resultSet.getString("payStyle"));
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
            e.printStackTrace();
        }finally
        {
            JdbcUtil.relase(resultSet,preparedStatement,connection);
        }
        return payWay;
    }
}
