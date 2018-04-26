package com.avp.mems.backstage.conf;

import com.avp.mems.backstage.entity.user.converters.StringToProjectLocationPK;
import com.avp.mems.backstage.entity.user.converters.StringToProjectMaintenanceEquipmentTypePK;
import com.avp.mems.backstage.entity.user.converters.StringToUserProjectPK;
import com.avp.mems.backstage.entity.user.converters.StringToUserRolePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;


/**
 * Created by pw on 2017/5/22.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.avp.mems.backstage.repositories.user"},
        entityManagerFactoryRef = "entityManagerFactoryUser",
        transactionManagerRef = "transactionManagerUser")
public class RepositoryUserConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("dataSourceUser")
    private DataSource dataSourceUser;

    Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }


    @Bean(name = "entityManagerFactoryUser")
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceUser);
        em.setPackagesToScan(new String[]{"com.avp.mems.backstage.entity.user"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(getVendorProperties(dataSourceUser));
        em.setJpaProperties(additionalJpaProperties());

        return em;
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager userTransactionManager() {
        return new JpaTransactionManager(userEntityManager().getObject());
        //return new JtaTransactionManager();
    }

//    @Autowired
//    @Qualifier("defaultConversionService")
//    public void setGenericConversionService(GenericConversionService genericConversionService) {
//        StringToUserRolePK urConverter = new StringToUserRolePK();
//        genericConversionService.addConverter(urConverter);
//
//        StringToProjectLocationPK plConverter = new StringToProjectLocationPK();
//        genericConversionService.addConverter(plConverter);
//
//        StringToProjectMaintenanceEquipmentTypePK pmetConverter = new StringToProjectMaintenanceEquipmentTypePK();
//        genericConversionService.addConverter(pmetConverter);
//
////        StringToUserPro cjectPK upConverter = new StringToUserProjectPK();
////        genericConversionService.addConverter(upConverter);
//    }
}