package com.primeton.wbd;

import com.github.pagehelper.PageInfo;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.org.controller.OrgController;
import com.primeton.wbd.org.dao.IOrgDao;
import com.primeton.wbd.org.model.OrgBean;
import com.primeton.wbd.user.controller.UserController;
import com.primeton.wbd.user.dao.IUserDao;
import com.primeton.wbd.user.model.UserBean;
import com.primeton.wbd.user.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WangbendongDemoApplicationTests
{
    @Autowired
    private UserController userController;

    @Autowired
    private OrgController orgController;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrgDao orgDao;

    /**
     * 用户管理测试
     */
    @Test
    public void userTestCase() throws ServiceException
    {
        //构造测试user信息
        UserBean user = buildTestUserBean();
        //测试创建用户
        testCreateUser(user);
        //测试根据ID查询
        testGetUser(user);
        //测试查询列表
        testQueryUsers(user);
        //测试修改用户信息
        testModifyUser(user);
        //测试登陆
        testSignIn(user);
        //测试删除用户
        testRemoveUser(user);
    }

    /**
     * 组织机构管理测试
     */
    @Test
    public void orgTestCase() throws ServiceException
    {
        //构造orgBean
        OrgBean org = buildOrgBean();
        //测试创建组织机构
        testCreateOrg(org);
        //测试根据ID查询组织机构
        testGetOrg(org);
        //测试查询组织机构列表
        testQueryOrgs(org);
        //测试修改组织机构
        testModifyOrg(org);
        //测试删除组织机构
        testRemoveOrg(org);
    }

    /**
     * 测试删除用户
     *
     * @param user
     * @throws ServiceException
     */
    private void testRemoveUser(UserBean user) throws ServiceException
    {
        //删除用户
        userController.removeUser(user.getId());

        Assert.assertNull("删除用户信息异常", userController.getUser(user.getId()));
    }

    /**
     * 测试登录
     *
     * @param user
     * @throws ServiceException
     */
    private void testSignIn(UserBean user) throws ServiceException
    {
        //登陆
        userService.signIn(user.getName(), user.getPassword());
    }

    /**
     * 测试修改用户
     *
     * @param user
     * @throws ServiceException
     */
    private void testModifyUser(UserBean user) throws ServiceException
    {
        user.setNick("wangbdTest1");
        user.setOrgId("01");

        //更新用户
        UserBean user1 = userController.modifyUser(user);

        Assert.assertEquals("用户信息修改异常", user1.getNick(), user.getNick());
        Assert.assertEquals("用户信息修改异常", user1.getOrgId(), user.getOrgId());
    }

    /**
     * 测试根据ID查询用户
     *
     * @param user
     */
    private void testGetUser(UserBean user)
    {
        //根据ID查询用户
        UserBean user1 = userController.getUser(user.getId());

        Assert.assertNotNull("根据ID获取用户信息异常", user1);
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getId(), user.getId());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getName(), user.getName());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getNick(), user.getNick());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getPassword(), user.getPassword());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getOrgId(), user.getOrgId());
    }

    /**
     * 测试查询用户列表
     *
     * @param user
     */
    private void testQueryUsers(UserBean user)
    {
        //查询用户列表
        PageInfo page = userController.queryUsers(user.getName(), 1, 1);

        Assert.assertNotNull("查询用户列表异常", page.getList());
        Assert.assertEquals("查询用户列表异常", page.getList().size(), 1);
        UserBean user1 = (UserBean) page.getList().get(0);
        Assert.assertEquals("查询用户列表异常", user1.getId(), user.getId());
        Assert.assertEquals("查询用户列表异常", user1.getName(), user.getName());
        Assert.assertEquals("查询用户列表异常", user1.getNick(), user.getNick());
        Assert.assertEquals("查询用户列表异常", user1.getPassword(), user.getPassword());
        Assert.assertEquals("查询用户列表异常", user1.getOrgId(), user.getOrgId());
    }

    /**
     * 测试创建用户
     *
     * @param user
     * @throws ServiceException
     */
    private void testCreateUser(UserBean user) throws ServiceException
    {
        //创建用户
        userController.createUser(user, null);

        Assert.assertNotNull("创建用户信息异常", user.getId());
    }

    /**
     * 构造测试user信息
     *
     * @return
     * @throws ServiceException
     */
    private UserBean buildTestUserBean() throws ServiceException
    {
        UserBean user = new UserBean();
        user.setName("wangbd@test");
        user.setPassword("www");
        user.setNick("wangbdTest");
        user.setOrgId("00");

        //先删防止插入用户名冲突
        UserBean user1 = userDao.queryUserByName(user.getName());
        if (user1 != null)
        {
            userController.removeUser(user1.getId());
            Assert.assertNull("测试删除用户信息异常", userDao.queryUserByName(user.getName()));
        }

        return user;
    }

    private void testRemoveOrg(OrgBean org) throws ServiceException
    {
        orgController.removeOrg(org.getOrgId());
        Assert.assertNull("删除组织机构异常", orgController.getOrg(org.getOrgId()));
    }

    private void testModifyOrg(OrgBean org) throws ServiceException
    {
        org.setOrgName("primeton@1");
        org.setParentId("01");

        //更新组织机构
        OrgBean org1 = orgController.modifyOrg(org);

        Assert.assertEquals("更新组织机构异常", org1.getOrgName(), org.getOrgName());
        Assert.assertEquals("更新组织机构异常", org1.getParentName(), org1.getParentName());
    }

    private OrgBean testGetOrg(OrgBean org)
    {
        OrgBean org1 = orgController.getOrg(org.getOrgId());

        Assert.assertNotNull("根据ID查询组织机构", org);

        Assert.assertEquals("查询用户列表异常或创建用户信息异常", org1.getOrgId(), org.getOrgId());
        Assert.assertEquals("查询用户列表异常或创建用户信息异常", org1.getOrgName(), org.getOrgName());
        Assert.assertEquals("查询用户列表异常或创建用户信息异常", org1.getParentId(), org.getParentId());

        return org;
    }

    private void testQueryOrgs(OrgBean org)
    {
        PageInfo page = orgController.queryOrgs(org.getOrgName(), 1, 1);

        Assert.assertNotNull("查询组织机构列表异常", page.getList());
        Assert.assertEquals("查询组织机构列表异常", page.getList().size(), 1);
        OrgBean org1 = (OrgBean) page.getList().get(0);
        Assert.assertEquals("查询组织机构列表异常", org1.getOrgId(), org.getOrgId());
        Assert.assertEquals("查询组织机构列表异常", org1.getOrgName(), org.getOrgName());
        Assert.assertEquals("查询组织机构列表异常", org1.getParentId(), org.getParentId());
    }

    private void testCreateOrg(OrgBean org) throws ServiceException
    {
        //创建组织机构
        orgController.createOrg(org, null);
    }

    private OrgBean buildOrgBean() throws ServiceException
    {
        OrgBean org = new OrgBean();
        org.setOrgId("primeton@");
        org.setOrgName("primeton");
        org.setParentId("00");

        //先删防止插入用户名冲突
        OrgBean org1 = orgDao.queryOrgByName(org.getOrgName());
        if (org1 != null)
        {
            orgController.removeOrg(org1.getOrgId());
            Assert.assertNull("测试删除组织机构异常", orgDao.queryOrgByName(org.getOrgName()));
        }

        return org;
    }

}
