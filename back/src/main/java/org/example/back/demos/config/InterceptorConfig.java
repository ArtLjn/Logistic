package org.example.back.demos.config;

import org.example.back.demos.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Token拦截器配置类
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/api/user/login");    // 登录请求不拦截
        excludePath.add("/api/user/register"); // 注册请求不拦截
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/perChase/**")
                .addPathPatterns("/api/trans/**")
                .excludePathPatterns(excludePath);
        //除了登陆接口其他所有接口都需要token验证
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
