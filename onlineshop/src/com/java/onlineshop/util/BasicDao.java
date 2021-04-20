package com.java.onlineshop.util;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBCcourse
 * @description: JDBC通用方法
 * @author: ShuangChenYue
 * @create: 2021-04-07 22:00
 **/
public class BasicDao
{

//======================================================================================================================

    //增、删、改三种的通用方法
    public static void updateData(Connection connection,String sql,Object...args)
    {
        PreparedStatement preparedStatement = null;
        try
        {
            //1.由于connection被传入了，所以这里直接进行sql预编译
            preparedStatement = connection.prepareStatement(sql);
            //2.迭代可变长参数，填充占位符
            for (int i=0;i<args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }
            //3.执行操作，返回int值
            int i = preparedStatement.executeUpdate();
            System.out.println(i+"条记录被影响。");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            //4.关闭资源
            JdbcUtil.relase(null,preparedStatement,null);//由于connection是被传入的，不是在此开启的，所以不用关
        }
    }

//======================================================================================================================

    //1.查询返回单列
    public static <E> E getColumnValue(String sql,Object...args)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //2.预编译sql语句，并返回preparedStatement
            preparedStatement = connection.prepareStatement(sql);
            //3.遍历可变长数组，并将数组中的数值填充给占位符
            for (int i=0;i<args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }
            //4.执行操作，处理结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                //5.返回结果集第1列的值，并强转
                return (E)resultSet.getObject(1);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            //关闭资源
            JdbcUtil.relase(resultSet,preparedStatement,connection);
        }
        //没查到的话就返回null
        return null;
    }

//======================================================================================================================

    //2.返回单个对象

    //传入一个对象，就返回这个对象的值
    public static <T> T getObject(Class<T> clazz,String sql,Object...args)
    {
        T object = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译传进来的sql
            preparedStatement = connection.prepareStatement(sql);
            //3.遍历可变长参数，将里面的值取出来，依次赋值给占位符
            for (int i=0;i< args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }
            //4.执行并返回结果集,我们要查的数据就放在结果集中
            resultSet = preparedStatement.executeQuery();
            /*
            1.我不知道数据库有几列
            2.我得想办法得到列的值
            3.我还得得到列名
             */
            //用结果集元数据，可以得到结果集中究竟有几列
            resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            //需要map集合 key存放列名 value存放列值
            Map<String,Object> map = new HashMap<String,Object>();
            if (resultSet.next())
            {
                for (int i=0;i<columnCount;i++)
                {
                    String labName = resultSetMetaData.getColumnLabel(i + 1);
                    Object value = resultSet.getObject(labName);
                    map.put(labName,value);
                }
            }
            if (map.size()>0)
            {
                //实例化
                object = clazz.newInstance();
                //给属性赋值
                for (Map.Entry<String,Object> maps: map.entrySet())
                {
                    //取得属性名
                    String name = maps.getKey().toLowerCase();
                    Object value = maps.getValue();
                    BeanUtils.setProperty(object,name,value);
                }
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
        return object;
    }

//======================================================================================================================


}
