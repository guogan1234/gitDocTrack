package com.avp.mem.njpb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 统一请求跳转配置
 *
 * @author Amber
 */
@Configuration
@SessionAttributes("authorizationRequest")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // main -- dashboard
        registry.addViewController("/dashboard").setViewName("njpb-pages/index");

        registry.addViewController("/").setViewName("njpb-pages/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }
}
