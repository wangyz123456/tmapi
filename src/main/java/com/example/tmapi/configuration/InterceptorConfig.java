package com.example.tmapi.configuration;

import com.example.tmapi.interceptor.CorsInterceptor;
import com.example.tmapi.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    CorsInterceptor corsInterceptor(){
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(loginInterceptor()).addPathPatterns("/pri/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
