package com.wbd.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wbd.org.dao.IOrgDao;
import com.wbd.user.model.UserBean;
import com.wbd.user.dao.IUserDao;
import com.wbd.user.service.IUserService;
import com.wbd.util.Utils;
import com.wbd.enums.ErrorEnum;
import com.wbd.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理service实现类
 * <p>
 * 实现用户的增删改查和用户登陆功能
 *
 * @author wangbendong
 * @version 1.0 2018.10.31
 * @since 1.8
 */
@Service("usersMangerService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService
{

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IOrgDao orgDao;

    /**
     * 根据id查询用户信息
     *
     * @param userId 用户名
     * @return 用户数据
     */
    @Override
    @Transactional(readOnly = true)
    public UserBean getUser(String userId)
    {
        return userDao.queryUserById(userId);
    }

    /**
     * 查询用户信息
     *
     * @param userName  用户名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @return 分页数据
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UserBean> queryUsers(String userName, int pageIndex, int pageSize)
    {
        PageHelper.startPage(pageIndex, pageSize);

        return new PageInfo<>(userDao.queryUsers(userName));
    }

    /**
     * 创建用户信息
     *
     * @param user 用户信息
     * @throws ServiceException
     */
    @Override
    public void createUser(UserBean user) throws ServiceException
    {
        //校验用户名和密码不能为空
        Utils.assertNotNull(user.getName(), ErrorEnum.LACK_USER_NAME);
        Utils.assertNotNull(user.getPassword(), ErrorEnum.LACK_USER_PASSWORD);

        //如果查询出结果抛出用户已存在错误
        Utils.assertNull(userDao.queryUserByName(user.getName()), ErrorEnum.ERROR_USER_INUSE);

        //存在组织机构id，校验填写的组织机构必须存在
        if (StringUtils.isNotEmpty(user.getOrgId()))
        {
            Utils.assertNotNull(orgDao.queryOrgById(user.getOrgId()), ErrorEnum.ERROR_ORG_ID);
        }

        userDao.insertUser(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新条数
     * @throws ServiceException
     */
    @Override
    public int modifyUser(UserBean user) throws ServiceException
    {
        //校验用户名和密码不能为空
        Utils.assertNotNull(user.getName(), ErrorEnum.LACK_USER_NAME);
        Utils.assertNotNull(user.getPassword(), ErrorEnum.LACK_USER_PASSWORD);

        //校验用户ID是否存在
        Utils.assertNotNull(userDao.queryUserById(user.getId()), ErrorEnum.ERROR_USER_ID);

        //判读修改用户名是否被使用
        UserBean userInfo = userDao.queryUserByName(user.getName());

        if (null != userInfo && !StringUtils.equals(userInfo.getId(), user.getId()))
        {
            throw new ServiceException(ErrorEnum.MODIFY_USER_NAME_INUSE);
        }

        //存在组织机构id，校验填写的组织机构必须存在
        if (StringUtils.isNotEmpty(user.getOrgId()))
        {
            Utils.assertNotNull(orgDao.queryOrgById(user.getOrgId()), ErrorEnum.ERROR_ORG_ID);
        }

        return userDao.updateUser(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除条数
     */
    @Override
    public int removeUser(String id)
    {
        return userDao.deleteUser(id);
    }

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = true)
    public UserBean signIn(String name, String password) throws ServiceException
    {
        UserBean user = userDao.queryUserByName(name);

        //如果未查询出结果抛出用户不存在错误
        Utils.assertNotNull(user, ErrorEnum.ERROR_USER_NAME);

        //密码错误抛出密码错误异常
        Utils.assertStrEquals(user.getPassword(), password, ErrorEnum.ERROR_USER_PASSWPRD);

        return user;
    }
}
