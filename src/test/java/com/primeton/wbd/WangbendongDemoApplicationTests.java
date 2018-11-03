package com.primeton.wbd;

import com.primeton.wbd.org.controller.OrgController;
import com.primeton.wbd.user.controller.UserController;
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
    private OrgController orgsMangerController;

    @Test
    public void testController()
    {
//        //查询所有的用户
//        JsonResult jsonResult = userController.queryAllUsers();
//
//        //保存用户
//        UserBean user = new UserBean();
//        user.setName("wangbd5");
//        user.setPassword("1231");
//
//        JsonResult jsonResult1 = userController.saveUser(user);
//
//        //根据id查询用户
//        JsonResult jsonResult2 = userController.queryUserById(user.getId());
//
//        //根据名称查询用户
//        JsonResult jsonResult3 = userController.queryUserByName(user.getName());
//
//        //更新用户
//        user.setName("wbd5");
//        JsonResult jsonResult4 = userController.updateUser(user);
//
//        //删除用户
//        //JsonResult jsonResult5 = userController.deleteUser(user.getId());
//
//        //查询所有的组织机构
//        JsonResult jsonResult6 = orgsMangerController.queryAllOrgs();
//
//        OrgBean orgBean = new OrgBean();
//        orgBean.setOrgId("05");
//        orgBean.setOrgName("sjdfha");
//
//        orgsMangerController.saveOrg(orgBean);
    }

}
