package com.primeton.wbd.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.wbd.user.dao.IUserDao;
import com.primeton.wbd.user.service.IUserService;
import com.primeton.wbd.org.dao.IOrgDao;
import com.primeton.wbd.user.model.UserBean;
import com.primeton.wbd.util.Utils;
import com.primeton.wbd.enums.ErrorEnum;
import com.primeton.wbd.exception.ServiceException;
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
 * @version 1.0
 * @date 2018.10.31
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
    public UserBean getUser(String userId) throws ServiceException
    {
        //校验是否为空
        Utils.assertNotNull(userId, ErrorEnum.LACK_ORG_ID.exception());

        return userDao.getUser(userId);
    }

    /**
     * 查询用户信息列表
     *
     * @param userName  用户名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @param isHasSub  根据单位单询时，是否也包含下级的人员
     * @return 分页数据
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UserBean> queryUsers(String userName, String orgId, Boolean isHasSub, Integer pageIndex, Integer pageSize)
    {
        //默认10页
        if (pageSize == null)
        {
            pageSize = 10;
        }
        //为0或为负时查询所有
        if (pageSize < 0)
        {
            pageSize = 0;
        }

        if (isHasSub == null)
        {
            isHasSub = false;
        }

        PageHelper.startPage(pageIndex, pageSize);

        return new PageInfo<>(userDao.queryUsers(userName, orgId, isHasSub));
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
        Utils.assertNotNull(user.getName(), ErrorEnum.LACK_USER_NAME.exception());
        Utils.assertNotNull(user.getPassword(), ErrorEnum.LACK_USER_PASSWORD.exception());

        //如果查询出结果抛出用户已存在错误
        Utils.assertNull(userDao.getUserByName(user.getName()), ErrorEnum.ERROR_USER_INUSE.exception());

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
    public UserBean modifyUser(UserBean user) throws ServiceException
    {
        //校验用户名不能为空
        Utils.assertNotNull(user.getName(), ErrorEnum.LACK_USER_NAME.exception());

        //校验用户ID是否存在
        Utils.assertNotNull(userDao.getUser(user.getId()), ErrorEnum.ERROR_USER_ID.exception());

        //判读修改用户名是否被使用
        UserBean userInfo = userDao.getUserByName(user.getName());

        if (null != userInfo && !StringUtils.equals(userInfo.getId(), user.getId()))
        {
            throw new ServiceException(ErrorEnum.MODIFY_USER_NAME_INUSE);
        }

        //更新数据
        userDao.updateUser(user);

        //返回修改数据，出去密码
        UserBean modifyUser = userDao.getUser(user.getId());

        return modifyUser;
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除条数
     */
    @Override
    public UserBean removeUser(String id) throws ServiceException
    {
        //校验是否为空
        Utils.assertNotNull(id, ErrorEnum.LACK_ORG_ID.exception());

        //先判断用户是否存在
        UserBean user = userDao.getUser(id);

        Utils.assertNotNull(user, ErrorEnum.ERROR_USER.exception());

        userDao.deleteUser(id);

        return user;
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
        UserBean user = userDao.getUserByName(name);

        //如果未查询出结果抛出用户不存在错误
        Utils.assertNotNull(user, ErrorEnum.ERROR_USER.exception());

        //密码错误抛出密码错误异常
        Utils.assertStrEquals(user.getPassword(), password, ErrorEnum.ERROR_USER_PASSWPRD.exception());

        return user;
    }

    /**
     * 更新用户密码
     *
     * @param id          用户ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return
     */
    @Override
    @Transactional()
    public void modifyPassword(String id, String oldPassword, String newPassword) throws ServiceException
    {
        //先判断用户是否存在
        UserBean user = userDao.getUser(id);

        Utils.assertNotNull(user, ErrorEnum.ERROR_USER.exception());

        //判断原密码
        Utils.assertStrEquals(oldPassword, user.getPassword(), ErrorEnum.ERROR_USER_PASSWPRD.exception());

        userDao.updatePassword(id, newPassword);
    }
}
