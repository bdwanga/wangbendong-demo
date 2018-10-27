package com.wbd.usersmanger.service;

import com.wbd.usersmanger.bean.UserBean;

import java.util.List;

public interface IUsersMangerService
{
    /**
     * 查询所有用户
     *
     * @return
     */
    public List<UserBean> queryAllUsers();

    /**
     * 保存用户
     *
     * @param user
     */
    public void saveUser(UserBean user);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return
     */
    public UserBean queryUserById(String id);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return
     */
    public UserBean queryUserByName(String name);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    public int updateUser(UserBean user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return
     */
    public int deleteUser(String id);

    /**
     * 用户登录
     *
     * @param name
     * @param password
     */
    public UserBean signIn(String name, String password);
}
