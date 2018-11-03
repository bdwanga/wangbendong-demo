package com.wbd.user.service;

import com.github.pagehelper.PageInfo;
import com.wbd.exception.ServiceException;
import com.wbd.user.model.UserBean;

/**
 * 用户管理service接口
 * <p>
 * 定义了用户的增删改查和用户登陆接口等
 *
 * @author wangbendong
 * @version 1.0 2018.10.31
 * @since 1.8
 */
public interface IUserService
{
    /**
     * 根据id查询用户信息
     *
     * @param userId  用户名
     * @return 用户数据
     */
    public UserBean getUser(String userId);

    /**
     * 查询用户信息
     *
     * @param userName  用户名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @return 分页数据
     */
    public PageInfo<UserBean> queryUsers(String userName, int pageIndex, int pageSize);

    /**
     * 创建用户信息
     *
     * @param user 用户信息
     * @throws ServiceException
     */
    public void createUser(UserBean user) throws ServiceException;

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新条数
     * @throws ServiceException
     */
    public int modifyUser(UserBean user) throws ServiceException;

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除条数
     * @throws ServiceException
     */
    public int removeUser(String id) throws ServiceException;

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     * @throws ServiceException
     */
    public UserBean signIn(String name, String password) throws ServiceException;
}
