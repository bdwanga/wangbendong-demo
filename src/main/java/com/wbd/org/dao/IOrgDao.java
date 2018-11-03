package com.wbd.org.dao;

import com.wbd.org.model.OrgBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织管理dao接口
 * <p>
 * 定义了用户的增删改查等接口
 *
 * @author wangbendong
 * @version 1.0 2018.10.31
 * @since 1.8
 */
@Mapper
public interface IOrgDao
{
    /**
     * 保存组织单位
     *
     * @param org
     */
    public void insertOrg(OrgBean org);

    /**
     * 根据ID查询组织单位
     *
     * @param orgId
     */
    public OrgBean queryOrgById(@Param(value = "orgId") String orgId);

    /**
     * 根据组织名称查询组织单位
     *
     * @param orgName
     */
    public OrgBean queryOrgByName(@Param(value = "orgName") String orgName);

    /**
     * 查询用户列表
     */
    public List<OrgBean> queryOrgs(@Param(value = "orgName") String orgName);

    /**
     * 更新组织单位
     *
     * @param org
     */
    public int updateOrg(OrgBean org);

    /**
     * 删除组织单位
     *
     * @param orgId
     */
    public int deleteOrg(@Param(value = "orgId") String orgId);
}
