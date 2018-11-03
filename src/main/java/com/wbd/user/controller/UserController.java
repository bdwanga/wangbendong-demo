package com.wbd.user.controller;

import com.github.pagehelper.PageInfo;
import com.wbd.exception.ServiceException;
import com.wbd.user.model.UserBean;
import com.wbd.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户管理Controller
 * <p>
 * 提供用户的增删改查和用户登陆等接口
 *
 * @author wangbendong
 * @version 1.0 2018.10.31
 * @since 1.8
 */
@Controller("userController")
@RequestMapping("/api/users")
@Api(value = "用户管理api", tags = "用户管理接口")
public class UserController
{
    @Autowired
    private IUserService userService;

    /**
     * 根据id查询用户信息
     *
     * @param userId 用户id
     * @return 用户数据
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询用户信息", response = UserBean.class)
    @ResponseBody
    public UserBean getUser(@PathVariable("userId") @ApiParam("用户ID") String userId)
    {
        return userService.getUser(userId);
    }


    /**
     * 查询用户信息列表
     *
     * @param userName  用户名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @return 分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询用户信息列表", response = PageInfo.class)
    @ResponseBody
    public PageInfo queryUsers(@RequestParam("userName") @ApiParam("用户名") String userName,
                               @RequestParam("pageIndex") @ApiParam("起始页数") int pageIndex,
                               @RequestParam("pageSize") @ApiParam("每页大小") int pageSize)
    {
        return userService.queryUsers(userName, pageIndex, pageSize);
    }

    /**
     * 创建用户信息
     *
     * @param user 用户信息
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建用户信息")
    public void createUser(@RequestBody @ApiParam("需要保存的用户信息") UserBean user, HttpServletResponse response) throws ServiceException
    {
        userService.createUser(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @throws ServiceException
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户信息")
    public void modifyUser(@RequestBody @ApiParam("需要更新的用户信息") UserBean user, HttpServletResponse response) throws ServiceException
    {
        userService.modifyUser(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param userId 用户ID
     * @throws ServiceException
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户")
    public void removeUser(@PathVariable("userId") @ApiParam("需要删除用户id") String userId) throws ServiceException
    {
        userService.removeUser(userId);
    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @throws ServiceException
     */
    @RequestMapping(value = "/actions/sign", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    @ResponseBody
    public void signIn(@RequestParam("userName") @ApiParam("用户名") String userName,
                       @RequestParam("password") @ApiParam("密码") String password,
                       HttpSession session,
                       HttpServletResponse response) throws ServiceException
    {
        //登录成功后将当前用户放入session
        session.setAttribute("curUser", userService.signIn(userName, password));
    }

    /**
     * 用户退出
     *
     * @param session 用户推出请求
     */
    @RequestMapping(value = "/actions/sign", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户退出")
    @ResponseBody
    public void signOut(HttpSession session, HttpServletResponse response)
    {
        //清除session中的所用信息
        session.invalidate();
    }
}
