package com.primeton.wbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WangbendongDemoApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(WangbendongDemoApplication.class, args);
    }
}
