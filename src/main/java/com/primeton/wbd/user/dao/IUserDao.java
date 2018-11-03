package com.primeton.wbd.user.dao;

import com.primeton.wbd.user.model.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao
{
    /**
     * 查询用户数据列表
     *
     * @param name 用户名，模糊查询
     * @return 用户信息列表
     */
    public List<UserBean> queryUsers(@Param(value = "name") String name);

    /**
     *  插入用户数据
     *
     * @param user 用户数据
     */
    public void insertUser(UserBean user);

    /**
     * 根据ID查询用户信息
     *
     * @param id 用户ID
     * @return
     */
    public UserBean queryUserById(@Param(value = "id") String id);

    /**
     * 根据用户名查询用户数据，精确匹配用户名
     *
     * @param name 用户名
     * @return
     */
    public UserBean queryUserByName(@Param(value = "name") String name);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return
     */
    public int updateUser(UserBean user);

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     * @return
     */
    public int deleteUser(@Param(value = "id") String id);
}
