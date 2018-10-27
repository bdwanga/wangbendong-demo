package com.wbd.orgsmanger.service.impl;

import com.wbd.exception.ServiceException;
import com.wbd.orgsmanger.bean.OrgBean;
import com.wbd.orgsmanger.dao.IOrgsMangerDao;
import com.wbd.orgsmanger.service.IOrgsMangerService;
import com.wbd.util.Utils;
import com.wbd.enums.ErrorEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理service
 */
@Service("orgMangerService")
@Transactional(rollbackFor = Exception.class)
public class OrgsMangerServiceImpl implements IOrgsMangerService
{
    @Autowired
    private IOrgsMangerDao OrgsMangerDao;

    @Override
    @Transactional
    public void saveOrg(OrgBean org)
    {
        //校验组织ID和组织名称不能为空
        Utils.assertNotNull(org.getOrgId(), ErrorEnum.LACK_ORG_ID);
        Utils.assertNotNull(org.getOrgName(), ErrorEnum.LACK_ORG_NAME);

        //如果查询出结果抛出组织ID已存在错误
        Utils.assertNull(OrgsMangerDao.queryOrgById(org.getOrgId()), ErrorEnum.ERROR_ORG_INUSE);

        OrgsMangerDao.saveOrg(org);
    }

    @Override
    @Transactional(readOnly = true)
    public OrgBean queryOrgById(String orgId)
    {
        return OrgsMangerDao.queryOrgById(orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgBean> queryAllOrgs()
    {
        return OrgsMangerDao.queryAllOrgs();
    }

    @Override
    @Transactional
    public int updateOrg(OrgBean org)
    {
        //组织名称不能为空
        Utils.assertNotNull(org.getOrgName(), ErrorEnum.LACK_ORG_NAME);

        //存校验填写的组织机构名称不能重复
        OrgBean orgInfo = OrgsMangerDao.queryOrgByName(org.getOrgName());

        if (null != orgInfo && !StringUtils.equals(orgInfo.getOrgId(), org.getOrgId()))
        {
            throw new ServiceException(ErrorEnum.MODIFY_ORG_NAME_INUSE);
        }

        return OrgsMangerDao.updateOrg(org);
    }

    @Override
    @Transactional
    public int deleteOrg(String orgId)
    {
        return OrgsMangerDao.deleteOrg(orgId);
    }
}
