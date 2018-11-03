package com.wbd.user.dao;

import com.wbd.user.model.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao
{
    public List<UserBean> queryUsers(@Param(value = "name") String name);

    public void insertUser(UserBean user);

    public UserBean queryUserById(@Param(value = "id") String id);

    public UserBean queryUserByName(@Param(value = "name") String name);

    public int updateUser(UserBean user);

    public int deleteUser(@Param(value = "id") String id);
}
