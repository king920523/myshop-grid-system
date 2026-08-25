package com.example.myshop.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/charger/add/**") // 🔒 全力防守：只要想新增充電站，一律都要檢查 Token！
                .excludePathPatterns("/auth/**");    // 🔓 特例放行：註冊和登入的路由絕對不能擋！
    }
}
