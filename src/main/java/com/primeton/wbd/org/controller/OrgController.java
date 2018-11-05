package com.primeton.wbd.org.controller;

import com.github.pagehelper.PageInfo;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.org.model.OrgBean;
import com.primeton.wbd.org.service.IOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 组织机构管理Controller
 * <p>
 * 提供组织机构的增删改查等接口
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@Controller("orgController")
@RequestMapping("/api/orgs")
@Api(value = "组织机构管理api", tags = "组织机构管理接口")
public class OrgController
{

    @Autowired
    private IOrgService orgService;

    /**
     * 根据id查询组织信息
     *
     * @param orgId 组织id
     * @return 用户数据
     */
    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询组织信息", response = OrgBean.class)
    @ResponseBody
    public OrgBean getOrg(@PathVariable("orgId") @ApiParam("用户ID") String orgId) throws ServiceException
    {
        return orgService.getOrg(orgId);
    }

    /**
     * 查询组织机构信息列表
     *
     * @param orgName   组织机构名
     * @param pageIndex 起始页数
     * @param pageSize  每页大小
     * @return 分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询组织机构信息列表", response = PageInfo.class)
    @ResponseBody
    public PageInfo<OrgBean> queryOrgs(@RequestParam("orgName") @ApiParam("用户名") String orgName,
                                       @RequestParam("pageIndex") @ApiParam("起始页数") int pageIndex,
                                       @RequestParam("pageSize") @ApiParam("每页大小") int pageSize)
    {
        return orgService.queryOrgs(orgName, pageIndex, pageSize);
    }

    /**
     * 创建组织单位
     *
     * @param org 组织机构信息
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建组织单位")
    public void createOrg(@RequestBody @ApiParam("需要保存的组织信息") OrgBean org, HttpServletResponse response) throws ServiceException
    {
        orgService.createOrg(org);
    }

    /**
     * 修改组织单位信息
     *
     * @param org 组织机构信息
     * @throws ServiceException
     */
    @RequestMapping(value = "/{orgId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改组织单位信息")
    @ResponseBody
    public OrgBean modifyOrg(@RequestBody @ApiParam("需要更新的组织信息") OrgBean org) throws ServiceException
    {
        return orgService.modifyOrg(org);
    }

    /**
     * 删除组织单位
     *
     * @param orgId 组织id
     * @throws ServiceException
     */
    @RequestMapping(value = "/{orgId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除组织")
    @ResponseBody
    public OrgBean removeOrg(@PathVariable("orgId") @ApiParam("需要删除组织id") String orgId) throws ServiceException
    {
        return orgService.removeOrg(orgId);
    }
}
