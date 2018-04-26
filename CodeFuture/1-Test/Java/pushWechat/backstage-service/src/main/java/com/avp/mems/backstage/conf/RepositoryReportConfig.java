package com.avp.mems.backstage.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * Created by Amber on 2017/6/19.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryReport",
        transactionManagerRef = "transactionManagerReport",
        basePackages = {"com.avp.mems.backstage.repositories.report"})
public class RepositoryReportConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("dataSourceReport")
    private DataSource dataSourceReport;

    Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    @Bean(name = "entityManagerFactoryReport")
    public LocalContainerEntityManagerFactoryBean reportEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceReport);
        em.setPackagesToScan(new String[]{"com.avp.mems.backstage.entity.report"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(getVendorProperties(dataSourceReport));
        em.setJpaProperties(additionalJpaProperties());

        return em;
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerReport")
    public PlatformTransactionManager reportTransactionManager() {
        return new JpaTransactionManager(reportEntityManager().getObject());
        //return new JtaTransactionManager();
    }

}