package com.primeton.wbd;

import com.github.pagehelper.PageInfo;
import com.primeton.wbd.config.SmsCodeConfig;
import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.org.controller.OrgController;
import com.primeton.wbd.org.dao.IOrgDao;
import com.primeton.wbd.org.model.OrgBean;
import com.primeton.wbd.user.controller.UserController;
import com.primeton.wbd.user.dao.IUserDao;
import com.primeton.wbd.user.model.UserBean;
import com.primeton.wbd.util.SmsUtil;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WangbendongDemoTestCase
{
    @Autowired
    private UserController userController;

    @Autowired
    private OrgController orgController;

    @Autowired
    private IUserDao userDao;

    private IOrgDao orgDao;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Autowired
    StringEncryptor stringEncryptor;

    @Autowired
    SmsCodeConfig smsCodeConfig;

    @Before
    public void setUp()
    {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testSmsConfig() {
        System.out.println("==================");
        System.out.println(SmsCodeConfig.accessKeyId);
        System.out.println(SmsCodeConfig.accessKeySecret);
        System.out.println("==================");
    }

    @Test
    public void testSendSms() throws ServiceException
    {
        System.out.println("==================");
        System.out.println("验证码："+SmsUtil.sendSmsCode("151929XXXXX"));
        System.out.println("==================");
    }

    @Test
    public void encryptPwd() {
        String result = stringEncryptor.encrypt("redis.");
        System.out.println("==================");
        System.out.println(result);
        System.out.println("==================");
        String result1 = stringEncryptor.decrypt(result);
        System.out.println("==================");
        System.out.println(result1);
        System.out.println("==================");
    }

    @Test
    public void testRest()
    {
        //RestTemplate restTemplate = new RestTemplate();
        //this.reInitMessageConverter(restTemplate);
        System.out.println(System.getProperty("@appId"));
        System.out.println(System.getProperty("user.home"));
        //InstanceId instanceInfo = getInstanceInfo(appInstance);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTime = new Date();
        Date endTime = new Date();
        String b = format.format(Long.valueOf(Long.valueOf(1541001600).longValue() * 1000L));
        //2019.3.20
        String end = format.format(Long.valueOf(Long.valueOf(1553875200).longValue() * 1000L));
        System.out.println(b);
        System.out.println(end);
        try
        {
            beginTime = format.parse(b);
            endTime = format.parse(end);

        } catch (Exception e){

        }

        System.out.println(format.format(beginTime));
        System.out.println(format.format(endTime));
    }

    private void reInitMessageConverter(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        Iterator var4 = converterList.iterator();

        while(var4.hasNext()) {
            HttpMessageConverter<?> item = (HttpMessageConverter)var4.next();
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }

        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);
    }

    @Test
    public void testJava8()
    {
        List<String> strs =  Arrays.asList("1","2","3","5");
        strs=
                strs.stream().sorted((x, y) -> y.compareTo(x) ).map((item) -> {System.out.println(item); return item;}).collect(Collectors.toList());
        System.out.println(strs);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        A b = new B();
        System.out.println(b.AA);
        System.out.println(b.getAA());
        System.out.println(((B)b).getAA());
    }

    class A {
        private String aa="123";

        String AA = "123";
        public String getAa() {
            return this.aa;
        }

        public String getAA() {
            return this.AA;
        }
    }

    class B extends A{
        //private String aa="abc";
        String AA = "abc";
        public String getAA() {
            return this.AA;
        }
    }

    /**
     * 用户管理测试
     */
    @Test
    public void testUser() throws Exception
    {
        //构造测试user信息
        UserBean user = buildTestUserBean();
        //测试创建用户
        testCreateUser(user);
        //测试根据ID查询
        testGetUser(user);
        //测试查询列表
        testQueryUsers(user);
        //测试修改用户信息
        testModifyUser(user);
        //测试登陆
        testSignIn(user);
        //测试修改密码
        testModifyPassword(user);
        //测试删除用户
        testRemoveUser(user);
    }

    /**
     * 组织机构管理测试
     */
    @Test
    public void testOrg() throws ServiceException
    {
        //构造orgBean
        OrgBean org = buildOrgBean();
        //测试创建组织机构
        testCreateOrg(org);
        //测试根据ID查询组织机构
        testGetOrg(org);
        //测试查询组织机构列表
        testQueryOrgs(org);
        //测试修改组织机构
        testModifyOrg(org);
        //测试删除组织机构
        testRemoveOrg(org);
    }

    /**
     * 测试删除用户
     *
     * @param user
     * @throws ServiceException
     */
    private void testRemoveUser(UserBean user) throws ServiceException
    {
        //删除用户
        userController.removeUser(user.getId());

        Assert.assertNull("删除用户信息异常", userController.getUser(user.getId()));
    }

    /**
     * 测试登录
     *
     * @param user
     * @throws ServiceException
     */
    private void testSignIn(UserBean user) throws Exception
    {
        // 构建请求
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users/actions/sign").param("userName", "wangbd").param(
                "password", "123");

        // 发送请求，获取请求结果
        ResultActions perform = mvc.perform(request);

        // 请求结果校验
        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
    }

    /**
     * 测试修改密码
     *
     * @param user
     * @throws ServiceException
     */
    private void testModifyPassword(UserBean user) throws ServiceException
    {
        //登陆
        userController.modifyPassword(user.getId(), user.getPassword(), "1234");

        UserBean user1 = userController.getUser(user.getId());

        Assert.assertEquals("测试修改用户密码异常", user1.getPassword(), "1234");
    }

    /**
     * 测试修改用户
     *
     * @param user
     * @throws ServiceException
     */
    private void testModifyUser(UserBean user) throws ServiceException
    {
        user.setNick("wangbdTest1");
        user.setOrgId("01");

        //更新用户
        UserBean user1 = userController.modifyUser(user);

        Assert.assertEquals("用户信息修改异常", user1.getNick(), user.getNick());
        Assert.assertEquals("用户信息修改异常", user1.getOrgId(), user.getOrgId());
    }

    /**
     * 测试根据ID查询用户
     *
     * @param user
     */
    private void testGetUser(UserBean user) throws ServiceException
    {
        //根据ID查询用户
        UserBean user1 = userController.getUser(user.getId());

        Assert.assertNotNull("根据ID获取用户信息异常", user1);
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getId(), user.getId());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getName(), user.getName());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getNick(), user.getNick());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getPassword(), user.getPassword());
        Assert.assertEquals("根据ID获取用户信息异常或创建用户信息异常", user1.getOrgId(), user.getOrgId());
    }

    /**
     * 测试查询用户列表
     *
     * @param user
     */
    private void testQueryUsers(UserBean user)
    {
        //查询用户列表
        PageInfo page = userController.queryUsers(user.getName(), user.getOrgId(), false, 1, 1);

        Assert.assertNotNull("查询用户列表异常", page.getList());
        Assert.assertEquals("查询用户列表异常", page.getList().size(), 1);
        UserBean user1 = (UserBean) page.getList().get(0);
        Assert.assertEquals("查询用户列表异常", user1.getId(), user.getId());
        Assert.assertEquals("查询用户列表异常", user1.getName(), user.getName());
        Assert.assertEquals("查询用户列表异常", user1.getNick(), user.getNick());
        Assert.assertEquals("查询用户列表异常", user1.getPassword(), user.getPassword());
        Assert.assertEquals("查询用户列表异常", user1.getOrgId(), user.getOrgId());
    }

    /**
     * 测试创建用户
     *
     * @param user
     * @throws ServiceException
     */
    private void testCreateUser(UserBean user) throws ServiceException
    {
        //创建用户
        userController.createUser(user);

        Assert.assertNotNull("创建用户信息异常", user.getId());
    }

    /**
     * 构造测试user信息
     *
     * @return
     * @throws ServiceException
     */
    private UserBean buildTestUserBean() throws ServiceException
    {
        UserBean user = new UserBean();
        user.setName("wangbd@test");
        user.setPassword("www");
        user.setNick("wangbdTest");
        user.setOrgId("00");

        //先删防止插入用户名冲突
        UserBean user1 = userDao.getUserByName(user.getName());
        if (user1 != null)
        {
            userController.removeUser(user1.getId());
            Assert.assertNull("测试删除用户信息异常", userDao.getUserByName(user.getName()));
        }

        return user;
    }

    private void testRemoveOrg(OrgBean org) throws ServiceException
    {
        orgController.removeOrg(org.getOrgId());
        Assert.assertNull("删除组织机构异常", orgController.getOrg(org.getOrgId()));
    }

    private void testModifyOrg(OrgBean org) throws ServiceException
    {
        org.setOrgName("primeton@1");
        org.setParentId("01");

        //更新组织机构
        OrgBean org1 = orgController.modifyOrg(org);

        Assert.assertEquals("更新组织机构异常", org1.getOrgName(), org.getOrgName());
        Assert.assertEquals("更新组织机构异常", org1.getParentName(), org1.getParentName());
    }

    private OrgBean testGetOrg(OrgBean org) throws ServiceException
    {
        OrgBean org1 = orgController.getOrg(org.getOrgId());

        Assert.assertNotNull("根据ID查询组织机构", org);

        Assert.assertEquals("查询用户列表异常或创建用户信息异常", org1.getOrgId(), org.getOrgId());
        Assert.assertEquals("查询用户列表异常或创建用户信息异常", org1.getOrgName(), org.getOrgName());
        Assert.assertEquals("查询用户列表异常或创建用户信息异常", org1.getParentId(), org.getParentId());

        return org;
    }

    private void testQueryOrgs(OrgBean org)
    {
        PageInfo page = orgController.queryOrgs(org.getOrgName(), org.getParentId(), 1, 1);

        Assert.assertNotNull("查询组织机构列表异常", page.getList());
        Assert.assertEquals("查询组织机构列表异常", page.getList().size(), 1);
        OrgBean org1 = (OrgBean) page.getList().get(0);
        Assert.assertEquals("查询组织机构列表异常", org1.getOrgId(), org.getOrgId());
        Assert.assertEquals("查询组织机构列表异常", org1.getOrgName(), org.getOrgName());
        Assert.assertEquals("查询组织机构列表异常", org1.getParentId(), org.getParentId());
    }

    private void testCreateOrg(OrgBean org) throws ServiceException
    {
        //创建组织机构
        orgController.createOrg(org);
    }

    private OrgBean buildOrgBean() throws ServiceException
    {
        OrgBean org = new OrgBean();
        org.setOrgId("primeton@");
        org.setOrgName("primeton");
        org.setParentId("00");

        //先删防止插入用户名冲突
        OrgBean org1 = orgDao.getOrgByName(org.getOrgName());
        if (org1 != null)
        {
            orgController.removeOrg(org1.getOrgId());
            Assert.assertNull("测试删除组织机构异常", orgDao.getOrgByName(org.getOrgName()));
        }

        //先删防止插入用户名冲突
        org1 = orgDao.getOrg(org.getOrgId());
        if (org1 != null)
        {
            orgController.removeOrg(org1.getOrgId());
            Assert.assertNull("测试删除组织机构异常", orgDao.getOrgByName(org.getOrgName()));
        }

        return org;
    }

}
