package com.wbd.usersmanger.dao;

import com.wbd.usersmanger.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUsersMangerDao
{
    public List<UserBean> queryAllUsers();

    public void saveUser(UserBean user);

    public UserBean queryUserById(String id);

    public UserBean queryUserByName(String name);

    public int updateUser(UserBean user);

    public int deleteUser(String id);
}
