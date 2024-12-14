package org.x1a0kang.compare.http.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 跨域
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                // 允许任何头
                .allowedHeaders("*")
                // 允许任何方法（post、get等）
                .allowedMethods("*")
                // 允许任何域名使用
                //.allowedOrigins("*")
                .allowedOriginPatterns("*")
                // 允许任何跨域cookies
                .allowCredentials(true)
                // cookie过期时间设置
                .maxAge(3600);
    }
}
