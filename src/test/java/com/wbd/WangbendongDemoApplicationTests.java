package com.wbd;

import com.wbd.orgsmanger.bean.OrgBean;
import com.wbd.orgsmanger.controller.OrgsMangerController;
import com.wbd.usersmanger.bean.UserBean;
import com.wbd.usersmanger.controller.UsersMangerController;
import com.wbd.usersmanger.service.IUsersMangerService;
import com.wbd.util.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WangbendongDemoApplicationTests
{

    @Autowired
    private IUsersMangerService IUsersMangerService;

    @Autowired
    private UsersMangerController usersMangerController;

    @Autowired
    private OrgsMangerController orgsMangerController;

    @Test
    public void testService()
    {
        //查询所有的用户
        List<UserBean> userList = IUsersMangerService.queryAllUsers();

        //保存用户
        UserBean user = new UserBean();
        user.setName("wangbd");
        user.setPassword("123");
        user.setId("12");

        IUsersMangerService.saveUser(user);

        //根据id查询用户
        UserBean user1 = IUsersMangerService.queryUserById("333c6d0b-e4a2-4596-9902-a5d98c2f665a");

        //根据name查询用户
        UserBean user2 = IUsersMangerService.queryUserByName("wangbd");

        //更新用户
        user2.setNick("wbd");
        IUsersMangerService.updateUser(user2);

    }

    @Test
    public void testController()
    {
        //查询所有的用户
        JsonResult jsonResult = usersMangerController.queryAllUsers();

        //保存用户
        UserBean user = new UserBean();
        user.setName("wangbd4");
        user.setPassword("1231");

        JsonResult jsonResult1 = usersMangerController.saveUser(user);

        //根据id查询用户
        JsonResult jsonResult2 = usersMangerController.queryUserById(user.getId());

        //根据名称查询用户
        JsonResult jsonResult3 = usersMangerController.queryUserByName(user.getName());

        //更新用户
        user.setName("wbd5");
        JsonResult jsonResult4 = usersMangerController.updateUser(user);

        //删除用户
        //JsonResult jsonResult5 = usersMangerController.deleteUser(user.getId());

        //查询所有的组织机构
        JsonResult jsonResult6 = orgsMangerController.queryAllOrgs();

        OrgBean orgBean = new OrgBean();
        orgBean.setOrgId("05");
        orgBean.setOrgName("sjdfha");

        orgsMangerController.saveOrg(orgBean);
    }

}
