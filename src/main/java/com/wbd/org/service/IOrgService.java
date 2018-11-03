package com.wbd.org.service;

import com.github.pagehelper.PageInfo;
import com.wbd.exception.ServiceException;
import com.wbd.org.model.OrgBean;

/**
 * 组织管理service接口
 * <p>
 * 定义了用户的增删改查和用户登陆接口等
 *
 * @author wangbendong
 * @version 1.0 2018.10.31
 * @since 1.8
 */
public interface IOrgService
{

    /**
     * 根据组织机构id，查询组织机构信息
     *
     * @param orgId
     * @return 组织机构信息
     */
    public OrgBean getOrg(String orgId);

    /**
     * 保存组织单位
     *
     * @param org
     * @throws ServiceException
     */
    public void createOrg(OrgBean org) throws ServiceException;

    /**
     * 获得组织机构信息列表
     *
     * @param orgName   用户名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @return 分页数据
     */
    public PageInfo<OrgBean> queryOrgs(String orgName, int pageIndex, int pageSize);

    /**
     * 更新组织单位
     *
     * @param org
     * @throws ServiceException
     */
    public int modifyOrg(OrgBean org) throws ServiceException;

    /**
     * 删除组织单位
     *
     * @param orgId
     */
    public int removeOrg(String orgId);
}
