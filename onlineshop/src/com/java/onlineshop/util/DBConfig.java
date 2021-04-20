package com.java.onlineshop.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @program: JDBCcourse
 * @description: 单例模式
 * @author: ShuangChenYue
 * @create: 2021-04-07 19:55
 **/
public class DBConfig
{
    private Properties properties = new Properties();//需要一个这个属性来读配置文件
    private static DBConfig dbConfig = null;
    private String path = "dbconfig.properties";//这里指定一下配置文件的路径，好让后面反射类加载器加载
    private DBConfig()//这是个私有的方法，在此方法里调用读取配置文件的方法，那可真是套娃啊
    {
        //这里直接调用了下面的read方法
        read();
    }

    //这里写读取配置文件的方法，好让上面那个私有的方法调用
    private void read()
    {
        try
        {
            properties.load(DBConfig.class.getClassLoader().getResourceAsStream(path));//加载配置文件
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //这里的方法是主要是根据传入的键值来获取键所对应的值，如我给形参传入了个dbuser，则返回root
    public String getValue(String key)
    {
        return properties.getProperty(key);
    }

    //接下来是个单例模式，获取一个DBConfig的对象，然后直接返回出去，之后要用这个对象的话，直接调这个方法就行
    public synchronized static DBConfig getInstance()
    {
        if (dbConfig == null)//意思是，dbconfig这个对象还没创建的话，就直接创建一个，将其返回就行
        {
            dbConfig = new DBConfig();
        }

        //将其返回
        return dbConfig;
    }

    //main方法测试一下，能否通过反射获取对象，再调一下方法
    public static void main(String[] args)
    {
        System.out.println(DBConfig.getInstance().getValue("driverClassName"));
    }

}
