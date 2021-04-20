package com.java.onlineshop.util;

import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @program: JDBCcourse
 * @description: 改版的工具类，理解起来有些麻烦，但还行吧
 * @author: ShuangChenYue
 * @create: 2021-04-07 19:23
 **/
public class JdbcUtil
{
    public static String DB_DRIVER = null; //这是驱动类的属性
    public static String DB_URL = null;//这是url的属性
    public static String DB_USER = null;//这是user的属性
    public static String DB_PASSWORD = null;//这是password的属性
    /*众所周知，我们获得数据库连接需要“驱动类、url、user、password”这四个东西，
      而这四个东西被我们写到了配置文件中，且通过DBconfig这个类将其加载，所以，
      我们只要调用这个类的方法，就能获得需要的四个东西。将其包在静态代码块中，只要我们
      调用这个类(JdbcUtil)，那么该代码块中的内容就会被自动加载进内存中，无需多次手动声明。
     */
    static
    {
        DB_DRIVER = DBConfig.getInstance().getValue("driverClassName");//给上面的属性赋值
        DB_URL = DBConfig.getInstance().getValue("dburl");
        DB_USER = DBConfig.getInstance().getValue("dbuser");
        DB_PASSWORD = DBConfig.getInstance().getValue("dbpassword");
    }

//======================================================================================================================

    //这边写开始连接数据库的通用工具类方法
    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        //1.加载驱动类
        Class.forName(DB_DRIVER);//反射，这个方法主要是给出类的路径，然后通过路径直接获得该类的对象
        //2.获得数据库连接
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        //3.返回连接，以便调用
        return connection;
    }

//======================================================================================================================

    //这边写关闭/释放资源的方法(本方法手段是，通过判断该对象不为空的话，才会关闭，因为若为空，关闭的话报空指针异常)
    public static void relase(ResultSet resultSet, Statement statement,Connection connection)
    {
        if (resultSet != null)
        {
            try
            {
                resultSet.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (statement != null)
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (connection != null)
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

//======================================================================================================================

    //这边写对关闭/释放资源方法的重载
    //(重载的手段是通过判断传入对象是否是指定类的实例，如果是的话，那就表示不为空，且将其强转为指定的类型,然后再关闭)
    public static void relase(Object object)
    {
        if (object instanceof ResultSet)//如果传入的参数是结果集的实例的话，将其强转后再关闭
        {
            ResultSet resultSet = (ResultSet) object;
            try
            {
                resultSet.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (object instanceof Statement)
        {
            Statement statement = (Statement) object;
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (object instanceof Connection)
        {
            Connection connection = (Connection) object;
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

//======================================================================================================================

    //这里写增、删、改的通用方法
    public static void updateData(String sql,Object...args)//用户届时调方法，传sql语句和占位符即可
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            //1.首先获取连接
            connection = JdbcUtil.getConnection();
            //2.预编译传进来的sql语句
            preparedStatement = connection.prepareStatement(sql);
            //3.遍历可变长参数的数组，然后挨个填充占位符
            for (int i=0;i< args.length;i++)
            {
                preparedStatement.setObject(i+1,args[i]);
            }
            //4.执行操作,返回一个int类型的值。
            int i = preparedStatement.executeUpdate();
            System.out.println(i+"条记录被影响。");
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            //关闭/释放被打开的资源
            JdbcUtil.relase(null,preparedStatement,connection);
        }
    }

//======================================================================================================================

    //列名=值
    public static void  printResult(ResultSet resultSet)
    {
        StringBuffer jpsb = new StringBuffer();
        try{
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            while(resultSet.next())
            {
                jpsb.append("[");
                for (int i =1;i<=count;i++)
                {
                    jpsb.append(resultSetMetaData.getColumnName(i)+" =" +
                            ""+resultSet.getObject(resultSetMetaData.getColumnName(i))+"  ");
                }
                jpsb.append("]").append("\n");
            }
            System.out.println(jpsb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//======================================================================================================================

    //main方法测试一下能不能成功获取连接
    public static void main(String[] args)
    {
        try
        {
            System.out.println(JdbcUtil.getConnection());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
