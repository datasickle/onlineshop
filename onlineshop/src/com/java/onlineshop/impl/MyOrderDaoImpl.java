package com.java.onlineshop.impl;

import com.java.onlineshop.dao.IMyOrderDao;
import com.java.onlineshop.pojo.*;
import com.java.onlineshop.util.JdbcUtil;
import com.mysql.cj.jdbc.JdbcConnection;
import sun.management.jdp.JdpController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: JDBCcourse
 * @description: IMyOrderDao的实现类
 * @author: ShuangChenYue
 * @create: 2021-04-18 21:21
 **/
public class MyOrderDaoImpl implements IMyOrderDao
{

    @Override
    public void addMyOrder(MyOrder myOrder)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "insert into myorder(name,cost,userId,paywayId,orderstatusID)values (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3.填充占位符
            preparedStatement.setString(1,myOrder.getName());
            preparedStatement.setDouble(2,myOrder.getCost());
            preparedStatement.setInt(3,myOrder.getUser().getId());
            preparedStatement.setInt(4,myOrder.getPayWay().getId());
            preparedStatement.setInt(5,myOrder.getOrderStatus().getOrderStatusId());
            //4.执行操作
            preparedStatement.executeUpdate();
            int orderid = 0;
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next())
                orderid = resultSet.getInt(1);
            JdbcUtil.relase(preparedStatement);
            //订单条目也得同时插入
            String linesql = "insert into orderitem(amount,productId,myorderId)values (?,?,?)";
            preparedStatement = connection.prepareStatement(linesql);
            //填充占位符
            Set<OrderItem> orderItemList = myOrder.getOrderlines();
            for (OrderItem orderItem:orderItemList)
            {
                preparedStatement.setInt(1,orderItem.getAmount());
                preparedStatement.setInt(2,orderItem.getProduct().getId());
                preparedStatement.setInt(3,orderid);
                preparedStatement.executeUpdate();
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
    }

    @Override
    public void updateMyOrder(MyOrder myOrder)
    {
        //更新订单只能修改地址，其他无法更改
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "update userinfo set street=?,city=? where infoid=? ";
            preparedStatement = connection.prepareStatement(sql);
            //infoid == userid
            //3.填充占位符
            preparedStatement.setString(1,myOrder.getUser().getInfo().getStreet());
            preparedStatement.setString(2,myOrder.getUser().getInfo().getCity());
            preparedStatement.setInt(3,myOrder.getUser().getId());
            //打印sql
            System.out.println(sql);
            //4.执行操作
            preparedStatement.executeUpdate();
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
            JdbcUtil.relase(null,preparedStatement,connection);
        }

    }

    @Override
    public void deleteMyOrder(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "delete from myorder where orderID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            JdbcUtil.relase(preparedStatement);
            sql = "delete from orderitem where myorderId=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
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
    public List<MyOrder> findAll()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<MyOrder> myOrderList = new ArrayList<MyOrder>();
        UserDaoImpl userDao = new UserDaoImpl();
        PayWayDaoImpl payWayDao = new PayWayDaoImpl();
        OrderStatusDaoImpl orderStatusDao = new OrderStatusDaoImpl();
        MyOrder myOrder = new MyOrder();
        User user = null;
        PayWay payWay = null;
        OrderStatus orderStatus = null;
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from myorder";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                myOrder.setOrderID(resultSet.getInt("orderID"));
                myOrder.setName(resultSet.getString("name"));
                myOrder.setCost(resultSet.getDouble("cost"));
                int userId = resultSet.getInt("userId");
                user = userDao.findById(userId);
                int paywayId = resultSet.getInt("paywayId");
                payWay = payWayDao.findById(paywayId);
                int orderstatusID = resultSet.getInt("orderstatusID");
                orderStatus = orderStatusDao.findById(orderstatusID);

                myOrder.setUser(user);
                myOrder.setPayWay(payWay);
                myOrder.setOrderStatus(orderStatus);
                myOrderList.add(myOrder);
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


        return myOrderList;
    }

    @Override
    public List<MyOrder> findByUserNameOrId(int userid)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserDaoImpl userDao = new UserDaoImpl();
        PayWayDaoImpl payWayDao = new PayWayDaoImpl();
        OrderStatusDaoImpl orderStatusDao = new OrderStatusDaoImpl();
        MyOrder myOrder = null;
        List<MyOrder> myOrderList = new ArrayList<MyOrder>();
        try
        {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from myorder where name=? or userId=?";
            preparedStatement = connection.prepareStatement(sql);
            //通过传入的userid去查找数据库中查找对应的user对象并返回，然后使用返回的对象调对应的username
            preparedStatement.setString(1,userDao.findById(userid).getName());
            preparedStatement.setInt(2,userid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                myOrder = new MyOrder();
                myOrder.setOrderID(resultSet.getInt("orderID"));
                myOrder.setName(resultSet.getString("name"));
                myOrder.setCost(resultSet.getDouble("cost"));
                int userId = resultSet.getInt("userId");
                User user = userDao.findById(userId);

                int paywayId = resultSet.getInt("paywayId");
                PayWay payWay = payWayDao.findById(paywayId);

                int orderstatusID = resultSet.getInt("orderstatusID");
                OrderStatus orderStatus = orderStatusDao.findById(orderstatusID);

                myOrder.setUser(user);
                myOrder.setPayWay(payWay);
                myOrder.setOrderStatus(orderStatus);
                myOrderList.add(myOrder);
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
        return myOrderList;
    }

    @Override
    public MyOrder findById(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MyOrder myOrder = new MyOrder();
        User user = null;
        PayWay payWay = null;
        OrderStatus orderStatus = null;
        UserDaoImpl userDao = new UserDaoImpl();
        PayWayDaoImpl payWayDao = new PayWayDaoImpl();
        OrderStatusDaoImpl orderStatusDao = new OrderStatusDaoImpl();
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "select * from myorder where orderID=?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setInt(1,id);
            //4.执行操作并返回结果集
            resultSet = preparedStatement.executeQuery();
            //5.处理结果集
            if (resultSet.next())
            {
                myOrder.setOrderID(resultSet.getInt("orderID"));
                System.out.println(resultSet.getInt("orderID"));
                myOrder.setName(resultSet.getString("name"));
                myOrder.setCost(resultSet.getDouble("cost"));

                int userId = resultSet.getInt("userId");
                user = userDao.findById(userId);
                int paywayId = resultSet.getInt("paywayId");
                payWay = payWayDao.findById(paywayId);
                int orderstatusID = resultSet.getInt("OrderstatusID");
                orderStatus = orderStatusDao.findById(orderstatusID);

                myOrder.setUser(user);
                myOrder.setPayWay(payWay);
                myOrder.setOrderStatus(orderStatus);
            }

            System.out.println(myOrder.getName()+":"+myOrder.getCost());
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

        return myOrder;
    }
}
