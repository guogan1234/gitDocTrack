/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb;

import com.avp.mem.njpb.controller.ActivitiController;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by Amber Wang on 2017-07-04 下午 04:03.
 */
@SpringBootApplication
@EnableResourceServer
public class BusinessApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessApplication.class);
@Autowired
    ActivitiController activitiController;



    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);


    }


    /*
     * 下面代码原意为：将自定义的标签解析器注入到activiti引擎中
     */
    @Bean
    public BeanPostProcessor activitiConfigurer() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof SpringProcessEngineConfiguration) {
                    /*List<BpmnParseHandler> customFormTypes = Arrays.<BpmnParseHandler>asList(new ExtensionUserTaskParseHandler());

                    ((SpringProcessEngineConfiguration) bean).setCustomDefaultBpmnParseHandlers(customFormTypes);*/

                    ((SpringProcessEngineConfiguration) bean).setJobExecutorActivate(true);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };

    }
}