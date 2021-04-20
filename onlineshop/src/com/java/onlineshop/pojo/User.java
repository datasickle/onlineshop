package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 用户实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:29
 **/
public class User
{
    //User表中的字段
    private Integer id;//id
    private String name;//姓名
    private String password;//密码
    private UserInfo info;//此处与Userinfo表建立联系

    //提供get，set方法和两个构造器


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UserInfo getInfo()
    {
        return info;
    }

    public void setInfo(UserInfo info)
    {
        this.info = info;
    }

    public User(Integer id, String name, String password, UserInfo info)
    {
        this.id = id;
        this.name = name;
        this.password = password;
        this.info = info;
    }

    public User()
    {
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", info=" + info +
                '}';
    }
}
