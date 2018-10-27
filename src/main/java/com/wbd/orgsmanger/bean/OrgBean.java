package com.wbd.orgsmanger.bean;

import java.io.Serializable;

public class OrgBean implements Serializable
{
    private static final long serialVersionUID = 4194249033374012474L;

    /**
     * 组织单位ID
     */
    private String orgId;

    /**
     * 组织单位名称
     */
    private String orgName;

    /**
     * 上级组织id
     */
    private String parentId;

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
}
