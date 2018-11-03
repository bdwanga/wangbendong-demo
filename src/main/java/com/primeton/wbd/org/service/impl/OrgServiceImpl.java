package com.primeton.wbd.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.wbd.enums.ErrorEnum;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.org.dao.IOrgDao;
import com.primeton.wbd.org.model.OrgBean;
import com.primeton.wbd.util.Utils;
import com.primeton.wbd.org.service.IOrgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组织管理service实现类
 * <p>
 * 实现了用户的增删改查和用户登陆接口等
 *
 * @author wangbendong
 * @version 1.0 2018.10.31
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
    public OrgBean getOrg(String orgId)
    {
        return orgDao.queryOrgById(orgId);
    }

    /**
     * 保存组织单位
     *
     * @param org
     * @throws ServiceException
     */
    @Override
    @Transactional
    public void createOrg(OrgBean org) throws ServiceException
    {
        //校验组织ID和组织名称不能为空
        Utils.assertNotNull(org.getOrgId(), ErrorEnum.LACK_ORG_ID);
        Utils.assertNotNull(org.getOrgName(), ErrorEnum.LACK_ORG_NAME);

        //如果查询出结果抛出组织ID已存在错误
        Utils.assertNull(orgDao.queryOrgById(org.getOrgId()), ErrorEnum.ERROR_ORG_INUSE);

        orgDao.insertOrg(org);
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
    public PageInfo<OrgBean> queryOrgs(String orgName, int pageIndex, int pageSize)
    {
        PageHelper.startPage(pageIndex, pageSize);

        return new PageInfo<OrgBean>(orgDao.queryOrgs(orgName));
    }

    /**
     * 更新组织单位
     *
     * @param org
     * @throws ServiceException
     */
    @Override
    @Transactional
    public int modifyOrg(OrgBean org) throws ServiceException
    {
        //组织名称不能为空
        Utils.assertNotNull(org.getOrgName(), ErrorEnum.LACK_ORG_NAME);

        //存校验填写的组织机构名称不能重复
        OrgBean orgInfo = orgDao.queryOrgByName(org.getOrgName());

        if (null != orgInfo && !StringUtils.equals(orgInfo.getOrgId(), org.getOrgId()))
        {
            throw new ServiceException(ErrorEnum.MODIFY_ORG_NAME_INUSE);
        }

        return orgDao.updateOrg(org);
    }

    /**
     * 删除组织单位
     *
     * @param orgId
     */
    @Override
    @Transactional
    public int removeOrg(String orgId)
    {
        return orgDao.deleteOrg(orgId);
    }
}
