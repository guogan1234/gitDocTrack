/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push;

import com.avp.mems.push.entities.converters.StringToPushInfoPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Hongfei
 */
//@RestController
@Configuration
//@EnableOAuth2Resource
@SpringBootApplication
@EnableOAuth2Sso
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    @Qualifier("defaultConversionService")
    public void setGenericConversionService(GenericConversionService genericConversionService) {
        StringToPushInfoPK converter = new StringToPushInfoPK();
        genericConversionService.addConverter(converter);
    }
    @Configuration
    @Order(-10)
    protected static class LoginConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //校验企业微信是否绑定请求需要开放
            http.antMatcher("/push").csrf().disable();

//            http.antMatcher("/workorderDetails/search/findById");

        }

    }
}
