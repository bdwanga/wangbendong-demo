package com.primeton.wbd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 短信验证码配置
 */
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsCodeConfig
{
    /**
     * 手机验证部分配置
     */

    // 设置超时时间-可自行调整
    public  final static String defaultConnectTimeout  = "sun.net.client.defaultConnectTimeout";
    public  final static String defaultReadTimeout = "sun.net.client.defaultReadTimeout";
    public  static String timeout = "10000";

    // 初始化ascClient需要的几个参数
    public  final static String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
    public  final static String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）

    // 替换成你的AK (产品密)
    public  static String accessKeyId;// 你的accessKeyId,填你自己的 上文配置所得  自行配置
    public  static String accessKeySecret;// 你的accessKeySecret,填你自己的 上文配置所得 自行配置

    // 必填:短信签名-可在短信控制台中找到
    public  static String signName; // 阿里云配置你自己的短信签名填入
    // 必填:短信模板-可在短信控制台中找到
    public  static String templateCode; // 阿里云配置你自己的短信模板填入


    public void setTimeout(String timeout)
    {
        SmsCodeConfig.timeout = timeout;
    }

    public void setAccessKeyId(String accessKeyId)
    {
        SmsCodeConfig.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret)
    {
        SmsCodeConfig.accessKeySecret = accessKeySecret;
    }

    public void setSignName(String signName)
    {
        SmsCodeConfig.signName = signName;
    }

    public void setTemplateCode(String templateCode)
    {
        SmsCodeConfig.templateCode = templateCode;
    }
}
