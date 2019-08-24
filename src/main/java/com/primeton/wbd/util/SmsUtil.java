package com.primeton.wbd.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.primeton.wbd.config.SmsCodeConfig;
import com.primeton.wbd.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SmsUtil
{
    private final static Logger log = LoggerFactory.getLogger(SmsUtil.class);

    /**
     * 阿里云短信发送验证码
     * @param mobile
     * @return
     */
    public static String sendSmsCode(String mobile) throws ServiceException{

        //校验手机号
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException("执行SmsUtil.setSmsCode方法错误，手机号为空未发送验证码");
        }

        //校验手机号
        if (StringUtils.isEmpty(SmsCodeConfig.accessKeyId)) {
            throw new ServiceException("执行SmsUtil.setSmsCode方法错误,短信配置尚未初始化");
        }

        /**
         * 短信验证---阿里大于工具
         */

        // 设置超时时间-可自行调整
        System.setProperty(SmsCodeConfig.defaultConnectTimeout, SmsCodeConfig.timeout);
        System.setProperty(SmsCodeConfig.defaultReadTimeout, SmsCodeConfig.timeout);

        // 初始化ascClient需要的几个参数
        final String product = SmsCodeConfig.product;// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = SmsCodeConfig.domain;// 短信API产品域名（接口地址固定，无需修改）

        // 替换成你的AK
        final String accessKeyId = SmsCodeConfig.accessKeyId;// 你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = SmsCodeConfig.accessKeySecret;// 你的accessKeySecret，参考本文档步骤2

        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, accessKeySecret);
        try {

            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

        } catch (ClientException e1) {
            throw new ServiceException("执行SmsUtil.setSmsCode方法错误", e1);
        }

        //获取验证码
        String code = vcode();

        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SmsCodeConfig.signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(SmsCodeConfig.templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{ \"code\":\""+code+"\"}");
        // 可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;

        try {

            sendSmsResponse = acsClient.getAcsResponse(request);
            if ("OK".equals(sendSmsResponse.getCode())) {
                // 请求成功
                log.info("发送验证码成功！！！");
            } else {
                //如果验证码出错，会输出错误码告诉你具体原因
                throw new ServiceException("发送验证码失败，错误码:{}, 错误信息：{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage());
            }

        } catch (Exception e) {
            throw new ServiceException("发送短信验证码失败，稍后重试", e);
        }

        return code;
    }

    /**
     * 生成6位随机数验证码
     * @return
     */
    public static String vcode(){
        Random random = new Random();

        // 随机生成6位验证码
        String num = String.valueOf(random.nextInt(900000)+100000);

        return num;
    }
}
