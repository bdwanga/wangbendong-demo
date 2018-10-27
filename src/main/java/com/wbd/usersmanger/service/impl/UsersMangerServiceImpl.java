package com.wbd.usersmanger.service.impl;

import com.wbd.orgsmanger.dao.IOrgsMangerDao;
import com.wbd.usersmanger.bean.UserBean;
import com.wbd.usersmanger.dao.IUsersMangerDao;
import com.wbd.usersmanger.service.IUsersMangerService;
import com.wbd.util.Utils;
import com.wbd.enums.ErrorEnum;
import com.wbd.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理service
 */
@Service("usersMangerService")
@Transactional(rollbackFor = Exception.class)
public class UsersMangerServiceImpl implements IUsersMangerService
{

    @Autowired
    private IUsersMangerDao usersMangerDao;

    @Autowired
    private IOrgsMangerDao orgsMangerDao;

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserBean> queryAllUsers()
    {
        return usersMangerDao.queryAllUsers();
    }

    /**
     * 保存用户
     *
     * @param user
     */
    @Override
    @Transactional
    public void saveUser(UserBean user)
    {
        //校验用户名和密码不能为空
        Utils.assertNotNull(user.getName(), ErrorEnum.LACK_USER_NAME);
        Utils.assertNotNull(user.getPassword(), ErrorEnum.LACK_USER_PASSWORD);

        //如果查询出结果抛出用户已存在错误
        Utils.assertNull(usersMangerDao.queryUserByName(user.getName()), ErrorEnum.ERROR_USER_INUSE);

        //存在组织机构id，校验填写的组织机构必须存在
        if (StringUtils.isNotEmpty(user.getOrgId()))
        {
            Utils.assertNotNull(orgsMangerDao.queryOrgById(user.getOrgId()), ErrorEnum.ERROR_ORG_ID);
        }

        usersMangerDao.saveUser(user);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserBean queryUserById(String id)
    {
        return usersMangerDao.queryUserById(id);
    }

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserBean queryUserByName(String name)
    {
        return usersMangerDao.queryUserByName(name);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    @Override
    @Transactional
    public int updateUser(UserBean user)
    {
        //校验用户名和密码不能为空
        Utils.assertNotNull(user.getName(), ErrorEnum.LACK_USER_NAME);
        Utils.assertNotNull(user.getPassword(), ErrorEnum.LACK_USER_PASSWORD);

        //判读修改用户名是否被使用
        UserBean userInfo = usersMangerDao.queryUserByName(user.getName());

        if (null != userInfo && !StringUtils.equals(userInfo.getId(), user.getId()))
        {
            throw new ServiceException(ErrorEnum.MODIFY_USER_NAME_INUSE);
        }

        //存在组织机构id，校验填写的组织机构必须存在
        if (StringUtils.isNotEmpty(user.getOrgId()))
        {
            Utils.assertNotNull(orgsMangerDao.queryOrgById(user.getOrgId()), ErrorEnum.ERROR_ORG_ID);
        }

        return usersMangerDao.updateUser(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return
     */
    @Override
    public int deleteUser(String id)
    {
        return usersMangerDao.deleteUser(id);
    }

    /**
     * 用户登录
     *
     * @param name
     * @param password
     */
    @Override
    @Transactional
    public UserBean signIn(String name, String password)
    {
        UserBean user = usersMangerDao.queryUserByName(name);

        //如果未查询出结果抛出用户不存在错误
        Utils.assertNotNull(user, ErrorEnum.ERROR_USER_NAME);

        //密码错误抛出密码错误异常
        Utils.assertStrEquals(user.getPassword(), password, ErrorEnum.ERROR_USER_PASSWPRD);

        return user;
    }
}
