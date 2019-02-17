package com.primeton.wbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableEurekaClient
public class WangbendongDemoApplication
{
    public static void main(String[] args)
    {
        /**  配置加解密跟秘钥，与配置文件的密文分开放  */
        //System.setProperty("jasypt.encryptor.password", "test");
        SpringApplication.run(WangbendongDemoApplication.class, args);
    }
}
