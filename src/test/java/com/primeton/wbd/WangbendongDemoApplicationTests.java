package com.primeton.wbd;

import com.github.pagehelper.PageInfo;
import com.primeton.wbd.enums.ErrorEnum;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.org.controller.OrgController;
import com.primeton.wbd.org.dao.IOrgDao;
import com.primeton.wbd.org.model.OrgBean;
import com.primeton.wbd.user.controller.UserController;
import com.primeton.wbd.user.dao.IUserDao;
import com.primeton.wbd.user.model.UserBean;
import com.primeton.wbd.user.service.IUserService;
import com.primeton.wbd.util.JsonResult;
import com.primeton.wbd.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

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
        //先查询判断是否存在
        UserBean userOld = userDao.queryUserByName("wangbd@primeton");

        UserBean user;
        //不存在
        if (null == userOld)
        {
            //构造用户
            user = new UserBean();
            user.setName("wangbd@primeton");
            user.setPassword("1231");
            user.setNick("wangbd");
            user.setOrgId("");
        }
        //存在
        else
        {
            user = userOld;
            userController.removeUser(user.getId());
        }

        //创建用户
        userController.createUser(user, null);

        //查询用户列表
        PageInfo page = userController.queryUsers("wangbd@primeton", 1, 1);

        if (page.getTotal() != 1)
        {
            throw new ServiceException(ErrorEnum.ERROR_QUERY_RESULT);
        }

        //根据ID查询用户
        user = userController.getUser(user.getId());

        if (null == user)
        {
            throw new ServiceException(ErrorEnum.ERROR_QUERY_RESULT);
        }

        //更新用户
        user = userController.modifyUser(user);

        //登陆
        userService.signIn(user.getName(),user.getPassword());

        if (null == userOld)
        {
            //删除用户
            userController.removeUser(user.getId());
        }
    }

    /**
     * 组织机构管理测试
     */
    @Test
    public void orgTestCase() throws ServiceException
    {
        //先查询判断是否存在
        OrgBean orgOld = orgDao.queryOrgById("primeton@");

        OrgBean org;
        //不存在
        if (null == orgOld)
        {
            //构造用户
            org = new OrgBean();
            org.setOrgId("primeton@");
            org.setOrgName("primeton");
            org.setParentId("00");
        }
        //存在
        else
        {
            org = orgOld;
            orgController.removeOrg(org.getOrgId());
        }

        //创建组织机构
        orgController.createOrg(org, null);

        //查询组织机构列表
        PageInfo page = orgController.queryOrgs("primeton", 1, 1);

        if (page.getTotal() != 1)
        {
            throw new ServiceException(ErrorEnum.ERROR_QUERY_RESULT);
        }

        //根据ID查询组织机构
        org = orgController.getOrg(org.getOrgId());

        if (null == org)
        {
            throw new ServiceException(ErrorEnum.ERROR_QUERY_RESULT);
        }

        //更新组织机构
        org = orgController.modifyOrg(org);

        if (null == orgOld)
        {
            //删除组织机构
            orgController.removeOrg(org.getOrgId());
        }
    }

}
