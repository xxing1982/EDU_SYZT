package com.syzton.sunread.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Properties;

/**
 * @author Petri Kainulainen
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.syzton.sunread.common.controller",
        "com.syzton.sunread.todo.controller",
        "com.syzton.sunread.controller"
})
@ImportResource({"classpath:security-configuration.xml",
"classpath:authentication-managers.xml",
"classpath:endpoint-configuration.xml",
"classpath:oauth2-configuration.xml",
"classpath:token-store.xml"})
public class WebAppContext extends WebMvcConfigurerAdapter {

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final long MAX_UPLOAD_FILE_SIZE = 10000000;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("TodoNotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
    
    @Bean 
    public CommonsMultipartResolver multipartResolver(){
    	CommonsMultipartResolver muliMultipartResolver = new CommonsMultipartResolver();
    	muliMultipartResolver.setMaxUploadSize(MAX_UPLOAD_FILE_SIZE);
        muliMultipartResolver.setDefaultEncoding(DEFAULT_ENCODING);
    	return muliMultipartResolver;
    }
    
    

}
