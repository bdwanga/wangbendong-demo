package com.wbd.usersmanger.controller;

import com.github.pagehelper.PageInfo;
import com.wbd.usersmanger.bean.UserBean;
import com.wbd.usersmanger.service.IUsersMangerService;
import com.wbd.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("usersMangerController")
@RequestMapping("/api/usersmanger")
@Api(value = "用户管理api", tags = "用户管理接口")
public class UsersMangerController
{
    @Autowired
    private IUsersMangerService usersMangerService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户信息", response = JsonResult.class)
    @ResponseBody
    public JsonResult<List<UserBean>> queryAllUsers()
    {
        List<UserBean> userList = usersMangerService.queryAllUsers();

        return new JsonResult<>(userList);
    }

    @RequestMapping(value = "/bypage" ,method = RequestMethod.GET)
    @ApiOperation(value = "分页查询", response = JsonResult.class)
    @ResponseBody
    public JsonResult<PageInfo> queryUsersByPage(@ApiParam("分页信息")PageInfo page)
    {
        PageInfo pageData = usersMangerService.queryUsersByPage(page);

        return new JsonResult<>(pageData);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "保存用户", response = JsonResult.class)
    @ResponseBody
    public JsonResult<UserBean> saveUser(@ApiParam("需要保存的用户信息") UserBean user)
    {
        usersMangerService.saveUser(user);

        return new JsonResult<>(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户ID查询用户", response = JsonResult.class)
    @ResponseBody
    public JsonResult<UserBean> queryUserById(@PathVariable("userId") @ApiParam("需要查询的用户id") String userId)
    {
        UserBean user = usersMangerService.queryUserById(userId);

        return new JsonResult<>(user);
    }

    @RequestMapping(value = "/byname/{userName}", method = RequestMethod.GET)
    @ApiOperation(value = "根据名称查询用户", response = JsonResult.class)
    @ResponseBody
    public JsonResult<UserBean> queryUserByName(@PathVariable("userName") @ApiParam("需要查询的用户名") String userName)
    {
        UserBean user = usersMangerService.queryUserByName(userName);

        return new JsonResult<>(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户信息", response = JsonResult.class)
    @ResponseBody
    public JsonResult updateUser(@ApiParam("需要更新的用户信息") UserBean user)
    {
        int count = usersMangerService.updateUser(user);

        return new JsonResult(JsonResult.SUCCESS, "更新用户信息成功，共更新" + count + "条");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户", response = JsonResult.class)
    @ResponseBody
    public JsonResult deleteUser(@PathVariable("userId") @ApiParam("需要删除用户id") String userId)
    {
        int count = usersMangerService.deleteUser(userId);

        return new JsonResult(JsonResult.SUCCESS, "删除用户信息成功，共删除" + count + "条");
    }

    @RequestMapping(value = "/actions/sign", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", response = JsonResult.class)
    @ResponseBody
    public JsonResult signIn(@RequestParam("userName") @ApiParam("用户名") String userName,
                             @RequestParam("password") @ApiParam("密码") String password,
                             HttpServletRequest request)
    {
        UserBean user = usersMangerService.signIn(userName, password);

        //登录成功将当前用户放入session
        HttpSession session = request.getSession();
        session.setAttribute("curUser", user);

        return new JsonResult(JsonResult.SUCCESS, "用户登录成功");
    }

    @RequestMapping(value = "/actions/sign", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户退出", response = JsonResult.class)
    @ResponseBody
    public JsonResult signOut(HttpServletRequest request)
    {
        //清除session中的所用信息
        HttpSession session = request.getSession();
        session.invalidate();

        return new JsonResult(JsonResult.SUCCESS, "用户退出成功");
    }
}
