package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource({"classpath:db.properties", "classpath:hibernate.properties"})
@EnableTransactionManagement
public class AppConfig {

    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("database_driver"));
        dataSource.setUrl(environment.getProperty("database_url"));
        dataSource.setUsername(environment.getProperty("database_username"));
        dataSource.setPassword(environment.getProperty("database_password"));
        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        //EntityManagerFactory Предоставляет возможность автоматического создания бинов и создание таблиц на основе бинов и др.
        em.setDataSource(getDataSource());
        em.setPackagesToScan(environment.getProperty("database_entity_package"));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());
        return em;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate_dialect"));
        properties.put("hibernate.show.sql", environment.getProperty("hibernate_show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate_hbm2ddl"));
        return properties;
    }

    @Bean
    JpaTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return transactionManager;
    }
}
