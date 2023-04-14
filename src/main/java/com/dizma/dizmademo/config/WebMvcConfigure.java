package com.dizma.dizmademo.config;

import com.dizma.dizmademo.interceptor.LogInterceptor;
import com.dizma.dizmademo.interceptor.MaintenanceInterceptorConf;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    private final MaintenanceInterceptorConf maintenanceInterceptorConf;
    private final LogInterceptor logInterceptor;

    public WebMvcConfigure(MaintenanceInterceptorConf maintenanceInterceptorConf, LogInterceptor logInterceptor) {
        this.maintenanceInterceptorConf = maintenanceInterceptorConf;
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(maintenanceInterceptorConf);
        registry.addInterceptor(logInterceptor).addPathPatterns("/products/**");
    }
}
