
package com.avp.mem.njpb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@EnableZuulProxy
@SpringBootApplication
public class NjpbWebApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(NjpbWebApplication.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(NjpbWebApplication.class, args);
/*
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            LOGGER.debug(beanName);
        }*/
    }
}

