package com.primeton.wbd.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "UserBean", description = "用户信息")
public class UserBean implements Serializable
{

    private static final long serialVersionUID = 7378899179007582933L;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户ID")
    private String id;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 令牌
     */
    @ApiModelProperty(value="令牌")
    private String token;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nick;

    /**
     * 单位id
     */
    @ApiModelProperty(value="单位ID")
    private String orgId;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String orgName;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getNick()
    {
        return nick;
    }

    public void setNick(String nick)
    {
        this.nick = nick;
    }

    public String getOrgId()
    {
        return orgId;
    }

    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
}
