package com.primeton.wbd.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.wbd.enums.ErrorEnum;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.org.dao.IOrgDao;
import com.primeton.wbd.org.model.OrgBean;
import com.primeton.wbd.org.service.IOrgService;
import com.primeton.wbd.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织管理service实现类
 * <p>
 * 实现了用户的增删改查和用户登陆接口等
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@Service("orgMangerService")
@Transactional(rollbackFor = Exception.class)
public class OrgServiceImpl implements IOrgService
{
    @Autowired
    private IOrgDao orgDao;

    /**
     * 根据组织机构id，查询组织机构信息
     *
     * @param orgId
     * @return 组织机构信息
     */
    public OrgBean getOrg(String orgId) throws ServiceException
    {
        //校验是否为空
        Utils.assertNotNull(orgId, ErrorEnum.LACK_ORG_ID);

        return orgDao.getOrg(orgId);
    }

    /**
     * 根据组织机构id，查询组织机构信息，包括下级节点
     *
     * @param orgId
     * @return 组织机构信息
     */
    public OrgBean getOrgDetail(String orgId) throws ServiceException
    {
        //校验是否为空
        Utils.assertNotNull(orgId, ErrorEnum.LACK_ORG_ID);

        //查询当前节点
        OrgBean org = orgDao.getOrg(orgId);

        if (org == null)
        {
            return org;
        }

        //查询所有子节点
        List<OrgBean> subs = orgDao.queryOrgSubs(orgId);

        //处理子节点
        org.setChildren(processOrgSubs(subs, org.getOrgId()));

        return org;
    }

    /**
     * 根据组织机构id,查询下级节点
     *
     * @param orgId
     * @return 组织机构信息
     */
    public List<OrgBean> getOrgSubs(String orgId) throws ServiceException
    {
        //校验是否为空
        Utils.assertNotNull(orgId, ErrorEnum.LACK_ORG_ID);

        //查询所有子节点
        List<OrgBean> subs = orgDao.queryOrgSubs(orgId);

        //处理子节点
        return processOrgSubs(subs, orgId);
    }

    /**
     * 处理所有子节点,只返回当前父节点下的子节点列表
     *
     * @param allSubs
     * @param rootParentId 根父节点
     */
    private List<OrgBean> processOrgSubs(List<OrgBean> allSubs, String rootParentId)
    {
        Map<String, List<OrgBean>> subMap = new HashMap<String, List<OrgBean>>();
        List<OrgBean> subs = new ArrayList<OrgBean>();

        //列表判空
        if(allSubs == null || allSubs.isEmpty())
        {
            return subs;
        }

        //将所有的子节点分组放入map
        for (OrgBean subOrg : allSubs)
        {
            List<OrgBean> subList = (List<OrgBean>) subMap.get(subOrg.getParentId());
            if (subList == null)
            {
                subList = new ArrayList<OrgBean>();
                subMap.put(subOrg.getParentId(), subList);
            }

            subList.add(subOrg);
        }

        //设置每个组织的子机构
        for (OrgBean subOrg : allSubs)
        {
            subOrg.setChildren(subMap.get(subOrg.getOrgId()));

            if (rootParentId.equals(subOrg.getParentId()))
            {
                subs.add(subOrg);
            }
        }

        return subs;
    }

    /**
     * 保存组织单位
     *
     * @param org
     * @throws ServiceException
     */
    @Override
    @Transactional
    public OrgBean createOrg(OrgBean org) throws ServiceException
    {
        //校验组织ID和组织名称不能为空
        Utils.assertNotNull(org.getOrgName(), ErrorEnum.LACK_ORG_NAME);

        //校验组织id和名称不能重复
        Utils.assertNull(orgDao.getOrgByName(org.getOrgName()), ErrorEnum.ERROR_ORG_NAME_INUSE);

        orgDao.insertOrg(org);

        return org;
    }

    /**
     * 查询组织机构信息列表
     *
     * @param orgName   用户名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @return 分页数据
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<OrgBean> queryOrgs(String orgName, String parentId, Integer pageIndex, Integer pageSize)
    {
        if (pageIndex == null)
        {
            pageIndex = 1;
        }
        if (pageSize == null)
        {
            pageSize = 10;
        }

        if (pageSize < 0)
        {
            pageSize = 0;
        }

        PageHelper.startPage(pageIndex, pageSize);

        return new PageInfo<OrgBean>(orgDao.queryOrgs(orgName, parentId));
    }

    /**
     * 更新组织单位
     *
     * @param org
     * @throws ServiceException
     */
    @Override
    @Transactional
    public OrgBean modifyOrg(OrgBean org) throws ServiceException
    {
        //组织名称不能为空
        Utils.assertNotNull(org.getOrgName(), ErrorEnum.LACK_ORG_NAME);

        //存校验填写的组织机构名称不能重复
        OrgBean orgInfo = orgDao.getOrgByName(org.getOrgName());

        if (null != orgInfo && !StringUtils.equals(orgInfo.getOrgId(), org.getOrgId()))
        {
            throw new ServiceException(ErrorEnum.MODIFY_ORG_NAME_INUSE);
        }

        orgDao.updateOrg(org);

        return orgDao.getOrg(org.getOrgId());
    }

    /**
     * 删除组织单位
     *
     * @param orgId
     */
    @Override
    @Transactional
    public OrgBean removeOrg(String orgId) throws ServiceException
    {
        //校验是否为空
        Utils.assertNotNull(orgId, ErrorEnum.LACK_ORG_ID);

        OrgBean org = orgDao.getOrg(orgId);

        Utils.assertNotNull(org, ErrorEnum.ERROR_ORG);

        //校验不能含有子节点
        Utils.assertNull(orgDao.queryOrgSubs(orgId), ErrorEnum.ERROR_ORG_SUB_DEL);

        orgDao.deleteOrg(orgId);

        return org;
    }
}
