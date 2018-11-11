package com.primeton.wbd.user.controller;

import com.github.pagehelper.PageInfo;
import com.primeton.wbd.user.service.IUserService;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.user.model.UserBean;
import com.primeton.wbd.util.JsonResult;
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
 * @version 1.0
 * @date 2018.10.31
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
    @ApiOperation(value = "根据id查询用户信息", response = JsonResult.class)
    @ResponseBody
    public UserBean getUser(@PathVariable("userId") @ApiParam(value = "用户ID", required = true) String userId) throws ServiceException
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
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询用户信息列表", response = JsonResult.class)
    @ResponseBody
    public PageInfo<UserBean> queryUsers(@RequestParam(value = "userName", required = false) @ApiParam("用户名,为空无该条件,模糊查询") String userName,
                                         @RequestParam(value = "orgId", required = false) @ApiParam("单位ID,为空无该条件，精确查询") String orgId,
                                         @RequestParam("pageIndex") @ApiParam(value = "起始页数", required = true) Integer pageIndex,
                                         @RequestParam(value = "pageSize", required = false) @ApiParam("每页大小,为零或负查询所有，默认10") Integer pageSize)
    {
        return userService.queryUsers(userName, orgId, pageIndex, pageSize);
    }

    /**
     * 创建用户信息
     *
     * @param user 用户信息
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建用户信息", response = JsonResult.class)
    @ResponseBody
    public void createUser(@RequestBody @ApiParam("需要保存的用户信息") UserBean user) throws ServiceException
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
    @ApiOperation(value = "修改用户信息", response = JsonResult.class)
    @ResponseBody
    public UserBean modifyUser(@RequestBody @ApiParam("需要更新的用户信息") UserBean user) throws ServiceException
    {
        return userService.modifyUser(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param userId 用户ID
     * @throws ServiceException
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", response = JsonResult.class)
    @ResponseBody
    public UserBean removeUser(@PathVariable("userId") @ApiParam("需要删除用户id") String userId) throws ServiceException
    {
        return userService.removeUser(userId);
    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @throws ServiceException
     */
    @RequestMapping(value = "/actions/sign", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", response = JsonResult.class)
    @ResponseBody
    public void signIn(@RequestParam("userName") @ApiParam(value = "用户名", required = true) String userName,
                       @RequestParam("password") @ApiParam(value = "密码", required = true) String password,
                       HttpSession session) throws ServiceException
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
    @ApiOperation(value = "用户退出", response = JsonResult.class)
    @ResponseBody
    public void signOut(HttpSession session)
    {
        //清除session中的所用信息
        session.invalidate();
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @throws ServiceException
     */
    @RequestMapping(value = "/actions/modifypassword", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", response = JsonResult.class)
    @ResponseBody
    public void modifyPassword(@RequestParam("userId") @ApiParam(value = "用户ID", required = true) String userId,
                               @RequestParam("oldPassword") @ApiParam(value = "原密码", required = true) String oldPassword,
                               @RequestParam("newPassword") @ApiParam(value = "新密码", required = true) String newPassword) throws ServiceException
    {
        userService.modifyPassword(userId, oldPassword, newPassword);
    }
}
