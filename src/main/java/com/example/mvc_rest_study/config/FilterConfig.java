package com.example.mvc_rest_study.config;

import com.example.mvc_rest_study.filter.AuthFilter;
import com.example.mvc_rest_study.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig{
    @Bean
    public FilterRegistrationBean<LoggingFilter> filterConfig(){
        FilterRegistrationBean<LoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LoggingFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/api/*");
        return bean;
    }
    @Bean
    public FilterRegistrationBean<AuthFilter> authConfig(){
        FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/api/*");
        return bean;
    }

}
