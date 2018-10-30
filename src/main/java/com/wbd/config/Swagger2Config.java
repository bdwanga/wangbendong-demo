package com.wbd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer
{
    /**
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     * swagger资源配置
     * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
     *
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.wbd"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     */
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                //页面标题
                .title("用户管理RESTful API")
                //创建人
                .contact(new Contact("wangbd", "", "wangbd@primeton.com"))
                //版本号
                .version("1.0")
                //描述
                .description("用户管理微服务,具有用户登录、用户管理、组织管理等功能。")
                .build();
    }
}
