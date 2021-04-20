package com.java.onlineshop.pojo;

/**
 * @program: JDBCcourse
 * @description: 用户信息实体
 * @author: ShuangChenYue
 * @create: 2021-04-01 12:30
 **/
public class UserInfo
{
    private Integer id;//id
    private String street;//街道
    private String city;//城市
    private String email;//邮箱
    private String phone;//号码
    private User user;//与user表建立联系
    private Country country;//与country表建立联系
    private Province province;//与province表建立联系

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }

    public Province getProvince()
    {
        return province;
    }

    public void setProvince(Province province)
    {
        this.province = province;
    }

    public UserInfo(Integer id, String street, String city, String email, String phone, User user, Country country, Province province)
    {
        this.id = id;
        this.street = street;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.user = user;
        this.country = country;
        this.province = province;
    }

    public UserInfo()
    {
    }
}
