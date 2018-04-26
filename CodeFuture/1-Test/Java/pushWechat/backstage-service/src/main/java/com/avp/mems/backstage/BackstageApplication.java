package com.avp.mems.backstage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

//import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by pw on 2017/5/22.
 */
//@ServletComponentScan
//@EnableConfigurationProperties
@Configuration
@EnableOAuth2Resource
@SpringBootApplication
@EnableScheduling
public class BackstageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackstageApplication.class);
    }

    @Primary
    @Qualifier("dataSourceUser")
    @Bean(name = "dataSourceUser")
    @ConfigurationProperties(prefix="spring.datasource.user")
    public DataSource userDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceBasic")
    @Qualifier("dataSourceBasic")
    @ConfigurationProperties(prefix="spring.datasource.basic")
    public DataSource basicDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceWork")
    @Qualifier("dataSourceWork")
    @ConfigurationProperties(prefix="spring.datasource.work")
    public DataSource workOrderDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourcePush")
    @Qualifier("dataSourcePush")
    @ConfigurationProperties(prefix="spring.datasource.push")
    public DataSource pushDataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dataSourceReport")
    @Qualifier("dataSourceReport")
    @ConfigurationProperties(prefix="spring.datasource.report")
    public DataSource reportDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Configuration
    @Order(-10)
    protected static class LoginConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //校验企业微信是否绑定请求需要开放
            http.antMatcher("/userWechats/**");

//            http.antMatcher("/workorderDetails/search/findById");

        }

    }

}
