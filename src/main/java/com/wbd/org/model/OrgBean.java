package com.wbd.org.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "OrgBean", description = "组织机构信息")
public class OrgBean implements Serializable
{
    private static final long serialVersionUID = 4194249033374012474L;

    /**
     * 组织单位ID
     */
    @ApiModelProperty(value="组织单位ID")
    private String orgId;

    /**
     * 组织单位名称
     */
    @ApiModelProperty(value="组织单位名称")
    private String orgName;

    /**
     * 上级组织id
     */
    @ApiModelProperty(value="上级组织ID")
    private String parentId;

    /**
     * 上级组织id
     */
    private String parentName;

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

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }
}
