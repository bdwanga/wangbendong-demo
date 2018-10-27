package com.wbd;

import com.wbd.interceptor.SignInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer
{

    /**
     * 配置拦截器拦截和不拦截的路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new SignInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/usersmanger/sign","/static/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
