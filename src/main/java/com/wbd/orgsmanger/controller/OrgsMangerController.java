package com.wbd.orgsmanger.controller;

import com.wbd.orgsmanger.bean.OrgBean;
import com.wbd.orgsmanger.service.IOrgsMangerService;
import com.wbd.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("orgMangerController")
@RequestMapping("/api/orgmanger")
@Api(value = "组织机构管理api", tags = "组织机构管理接口")
public class OrgsMangerController
{

    @Autowired
    private IOrgsMangerService orgsMangerService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有组织信息", response = JsonResult.class)
    @ResponseBody
    public JsonResult<List<OrgBean>> queryAllOrgs()
    {
        List<OrgBean> userList = orgsMangerService.queryAllOrgs();

        return new JsonResult<>(userList);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "保存组织信息", response = JsonResult.class)
    @ResponseBody
    public JsonResult<OrgBean> saveOrg(@ApiParam("需要保存的组织信息") OrgBean org)
    {
        orgsMangerService.saveOrg(org);

        return new JsonResult<>(org);
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据组织ID查询组织信息", response = JsonResult.class)
    @ResponseBody
    public JsonResult<OrgBean> queryOrgById(@PathVariable("orgId") @ApiParam("需要查询的组织id") String orgId)
    {
        OrgBean org = orgsMangerService.queryOrgById(orgId);

        return new JsonResult<>(org);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "更新组织信息", response = JsonResult.class)
    @ResponseBody
    public JsonResult updateOrg(@ApiParam("需要更新的组织信息") OrgBean org)
    {
        int count = orgsMangerService.updateOrg(org);

        return new JsonResult(JsonResult.SUCCESS, "更新组织信息成功，共更新" + count + "条");
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除组织", response = JsonResult.class)
    @ResponseBody
    public JsonResult deleteOrg(@PathVariable("orgId") @ApiParam("需要删除组织id") String orgId)
    {
        int count = orgsMangerService.deleteOrg(orgId);

        return new JsonResult(JsonResult.SUCCESS, "删除组织成功，共删除" + count + "条");
    }
}
