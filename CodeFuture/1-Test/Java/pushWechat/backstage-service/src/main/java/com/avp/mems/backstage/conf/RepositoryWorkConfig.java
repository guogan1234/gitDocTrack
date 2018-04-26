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
        entityManagerFactoryRef = "entityManagerFactoryWork",
        transactionManagerRef = "transactionManagerWork",
        basePackages = {"com.avp.mems.backstage.repositories.work"})
public class RepositoryWorkConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("dataSourceWork")
    private DataSource dataSourceWork;

    Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    @Bean(name = "entityManagerFactoryWork")
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceWork);
        em.setPackagesToScan(new String[]{"com.avp.mems.backstage.entity.work"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(getVendorProperties(dataSourceWork));
        em.setJpaProperties(additionalJpaProperties());

        return em;
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerWork")
    public PlatformTransactionManager userTransactionManager() {
        return new JpaTransactionManager(userEntityManager().getObject());
        //return new JtaTransactionManager();
    }

}