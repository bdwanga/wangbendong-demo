package com.primeton.wbd.user.dao;

import com.primeton.wbd.user.model.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理dao接口
 * <p>
 * 定义了用户的增删改查等接口
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@Mapper
public interface IUserDao
{
    /**
     * 查询用户数据列表
     *
     * @param name 用户名，模糊查询
     * @return 用户信息列表
     */
    public List<UserBean> queryUsers(@Param(value = "name") String name, @Param(value = "orgId") String orgId,@Param(value = "isHasSub") Boolean isHasSub);

    /**
     * 插入用户数据
     *
     * @param user 用户数据
     */
    public void insertUser(UserBean user);

    /**
     * 根据ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    public UserBean getUser(@Param(value = "id") String id);

    /**
     * 根据用户名查询用户数据，精确匹配用户名
     *
     * @param name 用户名
     * @return 用户信息
     */
    public UserBean getUserByName(@Param(value = "name") String name);

    /**
     * 根据用户手机号查询
     *
     * @param phone 手机号
     * @return 用户信息
     */
    public UserBean getUserByPhone(@Param(value = "phone") String phone);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 修改条数
     */
    public int updateUser(UserBean user);

    /**
     * 更新用户密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     * @return 修改条数
     */
    public int updatePassword(@Param(value = "id") String id, @Param(value = "newPassword") String newPassword);

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     * @return 删除条数
     */
    public int deleteUser(@Param(value = "id") String id);
}
