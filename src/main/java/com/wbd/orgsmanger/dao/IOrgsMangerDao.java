package com.wbd.orgsmanger.dao;

import com.wbd.orgsmanger.bean.OrgBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOrgsMangerDao
{
    /**
     * 保存组织单位
     *
     * @param org
     */
    public void saveOrg(OrgBean org);

    /**
     * 根据ID查询组织单位
     *
     * @param orgId
     */
    public OrgBean queryOrgById(String orgId);

    /**
     * 查询所有的用户
     *
     */
    public List<OrgBean> queryAllOrgs();

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
    public int deleteOrg(String orgId);
}
