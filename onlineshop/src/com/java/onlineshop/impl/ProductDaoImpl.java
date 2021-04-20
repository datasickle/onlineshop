package com.java.onlineshop.impl;

import com.java.onlineshop.dao.IProductDao;
import com.java.onlineshop.pojo.Category;
import com.java.onlineshop.pojo.Product;
import com.java.onlineshop.util.BasicDao;
import com.java.onlineshop.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBCcourse
 * @description: IProductDao的实现类：产品接口实现类
 * @author: ShuangChenYue
 * @create: 2021-04-18 13:39
 **/
public class ProductDaoImpl implements IProductDao
{

    @Override
    public void addProduct(Product product)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            //1.获取连接
            connection =JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "insert into product(name,price,category) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2,product.getPrice());
            preparedStatement.setInt(3,product.getCategory().getId());
            //4.填充完之后执行操作
            int i = preparedStatement.executeUpdate();
            System.out.println(i+"条记录被影响");
            //手动提交一下
            connection.commit();
        }catch (Exception e)
        {
            //数据回滚
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
    public void updateProduct(Product product)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "update product set name=?,price=?,category=? where id=?";
            //直接调用基本方法，具体请看util包下，当然也可以自己在这儿写，不过调方法肯定更简单一些
            BasicDao.updateData(connection,sql,product.getName(),product.getPrice(),product.getCategory().getId(),product.getId());
            //手动提交
            connection.commit();
        }catch (Exception e)
        {
            //数据回滚
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
    public Product findById(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;
        Category category = null;
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "select * from product where id=?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setInt(1,id);
            //4.执行操作，并返回结果集
            resultSet = preparedStatement.executeQuery();
            //5.处理结果及
            if (resultSet.next())
            {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                category = categoryDao.findById(resultSet.getInt("category"));
                product.setCategory(category);
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
        return product;
    }

    @Override
    public List<Product> findAll()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<Product>();
        Product product = null;
        Category category = null;
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "select * from product";
            preparedStatement = connection.prepareStatement(sql);
            //3.执行操作并返回结果集
            resultSet = preparedStatement.executeQuery();
            //4.处理结果集
            while(resultSet.next())
            {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                category =  categoryDao.findById(resultSet.getInt("category"));
                product.setCategory(category);
                //设置完category属性后，将整个对象存入集合中
                productList.add(product);
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
        return productList;
    }

    @Override
    public List<Product> findByLikeName(String name)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<Product>();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        Product product = null;
        Category category = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "select * from product where `name` like ?;";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setString(1,"%"+name+"%");
            //4.执行操作并返回结果集
            resultSet = preparedStatement.executeQuery();
            //5.处理结果集
            while(resultSet.next())
            {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                category = categoryDao.findById(resultSet.getInt("category"));
                product.setCategory(category);
                productList.add(product);
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
        return productList;
    }
}
