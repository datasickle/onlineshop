package com.java.onlineshop.impl;

import com.java.onlineshop.dao.ICountryDao;
import com.java.onlineshop.dao.IUserDao;
import com.java.onlineshop.pojo.Country;
import com.java.onlineshop.pojo.Province;
import com.java.onlineshop.pojo.User;
import com.java.onlineshop.pojo.UserInfo;
import com.java.onlineshop.util.BasicDao;
import com.java.onlineshop.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JDBCcourse
 * @description: 对UserDao接口的实现
 * @author: ShuangChenYue
 * @create: 2021-04-08 12:19
 **/
public class UserDaoImpl implements IUserDao
{

    @Override
    //在测试类中构造对象后调用此方法，再将对象传入即可
    public void addUser(User user)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //2.禁用自动提交，我们手动提交
            connection.setAutoCommit(false);
            //3.预编译sql文件，我们需要添加
            String sql = "insert into user(name,password) values(?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//返回主键
            //4.填充占位符
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            //5.执行操作
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();//获取主键返回结果集
            //6.把主键拿出来
            int key=0;
            while (resultSet.next())
            {
                key=resultSet.getInt(1);//拿出结果集中下标为1的值：注：是从1开始，而不是从0
            }
            //7.将preparedStatement关闭
            JdbcUtil.relase(preparedStatement);

            UserInfo info = user.getInfo();
            //因为国家和省份都是外键，所以要先单独获取
            //得到国家id
            int countryId = user.getInfo().getCountry().getId();
            //得到省份id
            int provinceId = user.getInfo().getProvince().getId();

            //给userInfo表插信息，预编译sql语句
            String infosql = "insert into userinfo values(?,?,?,?,?,?,?) ";
            preparedStatement = connection.prepareStatement(infosql);
            //填充占位符
            preparedStatement.setInt(1,key);//将userinfoId设置为user的id(主键)
            preparedStatement.setString(2,info.getStreet());
            preparedStatement.setString(3,info.getCity());
            preparedStatement.setString(4,info.getEmail());
            preparedStatement.setString(5,info.getPhone());
            preparedStatement.setString(6, String.valueOf(countryId));
            preparedStatement.setString(7, String.valueOf(provinceId));

            //执行操作
            preparedStatement.executeUpdate();

            //操作完之后手动提交
            connection.commit();

        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            //关闭资源
            JdbcUtil.relase(resultSet,preparedStatement,connection);
        }


    }

    @Override
    public void updateUser(User user)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //预编译sql
            String sql = "update user set name=?,password=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            BasicDao.updateData(connection,sql,user.getName(),user.getPassword(),user.getId());
            //到这一步其实user表中的信息已经更新好了。但由于更新user表就必须更新userInfo，所以还得更新userInfo表
            sql = "update userinfo set street=?,city=?,email=?,phone=?,countryId=?,provinceId=? where id=?";
            UserInfo info = new UserInfo();
            BasicDao.updateData(connection,
                    sql,
                    info.getStreet(),
                    info.getCity(),
                    info.getEmail(),
                    info.getPhone(),
                    info.getCountry().getId(),
                    info.getProvince().getId(),
                    info.getId());
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
            JdbcUtil.relase(connection);
        }
    }

    @Override
    public User findByNameAndPassword(String name, String password)
    {
        ICountryDao countryDao = new CountryDaoImpl();
        //返回的用户，一定得有用户信息
        User user = BasicDao.getObject(User.class, "select id as id,name name,password password from user where name=? and password=?",name,password);
        int user_id = user.getId();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo info = null;
        try
        {
            //1.获得连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql语句
            String sql = "select * from userinfo where id=?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setInt(1,user_id);
            //4.执行并返回结果集
            resultSet = preparedStatement.executeQuery();
            //5.处理结果集，将结果集中的值保存进userinfo的对象中
            if (resultSet.next())
            {
                info = new UserInfo();
                info.setId(user_id);
                info.setStreet(resultSet.getString("street"));
                info.setCity(resultSet.getString("city"));
                info.setEmail(resultSet.getString("email"));
                info.setPhone(resultSet.getString("phone"));

                int country_id = Integer.parseInt(resultSet.getString("countryId"));
                Country country = countryDao.findById(country_id);
                country.setId(country_id);

                int province_id = resultSet.getInt("provinceId");
                Province province = new Province();
                province.setId(province_id);
                info.setProvince(province);
                info.setCountry(country);
                info.setUser(user);
                user.setInfo(info);
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

        return user;
    }

    @Override
    public List<User> findAll()
    {
        //1.需要集合去存储查找所返回的user信息
        List<User> userList = new ArrayList<User>();
        //连接只需要开启一次，所以只用事先声明一个
        Connection connection = null;
        //preparedStatement需要两个，user和userinfo各一个
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        //结果集也需要两个，分别用来存user和userinfo
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        //Country和Province也需要各来一个，userinfo要用到
        Country country = null;
        Province province = null;
        //需要一个User类型的对象保存查询返回的值
        User user = null;
        //同样也需要一个Userinfo
        UserInfo info = null;
        //开始
        try
        {
           //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
           //2.预编译sql语句
            String sql = "select * from user";
            String infosql = "select * from userinfo where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement1 = connection.prepareStatement(infosql);
            //3.返回查询结果集，先返回user的结果集，一个一个来
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                //若结果集中有数据，则新实例化一个user对象，用来存结果集中的数据
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                //再给infosql填充占位符
                preparedStatement1.setInt(1,resultSet.getInt("id"));
                //填充完之后，infosql语句完整再来执行userinfo的sql语句，并返回结果集
                resultSet1 = preparedStatement1.executeQuery();
                //再来处理userinfo的结果集
                while (resultSet1.next())
                {
                    info = new UserInfo();
                    info.setId(resultSet1.getInt("id"));
                    info.setStreet(resultSet1.getString("street"));
                    info.setCity(resultSet1.getString("city"));
                    info.setEmail(resultSet1.getString("email"));
                    info.setPhone(resultSet1.getString("phone"));
                    country = new Country();
                    country.setId(resultSet1.getInt("countryId"));
                    province = new Province();
                    province.setId(resultSet1.getInt("provinceId"));
                    info.setCountry(country);
                    info.setProvince(province);
                    info.setUser(user);
                    user.setInfo(info);
                }
                userList.add(user);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            JdbcUtil.relase(resultSet,preparedStatement,null);
            JdbcUtil.relase(resultSet,preparedStatement1,connection);
        }
        return userList;
    }

    @Override
    public User findById(int id)
    {
        ICountryDao countryDao = new CountryDaoImpl();
        User user = BasicDao.getObject(User.class, "select id,name,password from user where id =?", id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo info = null;
        try
        {
            //1.获取连接
            connection = JdbcUtil.getConnection();
            //设置手动提交
            connection.setAutoCommit(false);
            //2.预编译sql
            String sql ="select * from userinfo where id=?";
            preparedStatement = connection.prepareStatement(sql);
            //2.5填充占位符
            preparedStatement.setInt(1,id);
            //3.执行并返回结果集
            resultSet = preparedStatement.executeQuery();
            //4.处理结果集
            if (resultSet.next())
            {
                info = new UserInfo();
                info.setId(id);
                info.setStreet(resultSet.getString("street"));
                info.setCity(resultSet.getString("city"));
                info.setEmail(resultSet.getString("email"));
                info.setPhone(resultSet.getString("phone"));
                /*我们得到的countryId只是数据库里的，而我们的类里面只要Country，是通过Country.getId，
                每次才能得到info的CountryId，所以拿到的countryId不能直接保存到info对象的Country里，因为info
                 里只有Country这个类，所以我们得拿得到的countryId去数据库中找到一个Country对象，然后把这个Country
                 对象的Id设置成我们得到的countryId，然后再把这个country保存进info的Country，这样就能通过country.getId
                 的方式拿到对应的countryId了，province同理。
                */
                //我们从结果集中拿到了info中的countryId，因为这个countryId是Integer类型的，我们得先把他转成基本数据类型int
                int countryId = Integer.parseInt(resultSet.getString("countryId"));
                //转型成功后，用这个countryId去调countryDao里的findById方法，通过id查国家
                Country country = countryDao.findById(countryId);
                //拿到查出来的国家后，将国家id设置为我们拿到的countryId
                country.setId(countryId);
                //拿到对应的provinceId
                int provinceId = resultSet.getInt("provinceId");
                //new一个province对象
                Province province = new Province();
                //将其id设置为得到的provinceId
                province.setId(provinceId);
                //将info中的Country和Province与上面两个绑定
                info.setCountry(country);
                info.setProvince(province);
                //将得到的最上面找到的user也绑定起来
                info.setUser(user);
                //user也要绑定info
                user.setInfo(info);
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
        return user;
    }
}
