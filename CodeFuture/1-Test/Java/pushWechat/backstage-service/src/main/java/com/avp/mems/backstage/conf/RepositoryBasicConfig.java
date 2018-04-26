package com.avp.mems.backstage.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;


/**
 * Created by pw on 2017/5/22.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryBasic",
        transactionManagerRef = "transactionManagerBasic",
        basePackages = {"com.avp.mems.backstage.repositories.basic"})
public class RepositoryBasicConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("dataSourceBasic")
    private DataSource dataSourceBasic;

    Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    @Primary
    @Bean(name = "entityManagerFactoryBasic")
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceBasic);
        em.setPackagesToScan(new String[]{"com.avp.mems.backstage.entity.basic"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(getVendorProperties(dataSourceBasic));
        em.setJpaProperties(additionalJpaProperties());

        return em;
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Primary
    @Bean(name = "transactionManagerBasic")
    public PlatformTransactionManager userTransactionManager() {
        return new JpaTransactionManager(userEntityManager().getObject());
        //return new JtaTransactionManager();
    }

}