package com.avp.mems.backstage.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Amber on 2017/6/2.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPush",
        transactionManagerRef = "transactionManagerPush",
        basePackages = {"com.avp.mems.backstage.repositories.push"})
public class RepositoryPushConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("dataSourcePush")
    private DataSource dataSourcePush;

    Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    @Bean(name = "entityManagerFactoryPush")
    public LocalContainerEntityManagerFactoryBean pushEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourcePush);
        em.setPackagesToScan(new String[]{"com.avp.mems.backstage.entity.push"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(getVendorProperties(dataSourcePush));
        em.setJpaProperties(additionalJpaProperties());

        return em;
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerPush")
    public PlatformTransactionManager pushTransactionManager() {
        return new JpaTransactionManager(pushEntityManager().getObject());
        //return new JtaTransactionManager();
    }

}