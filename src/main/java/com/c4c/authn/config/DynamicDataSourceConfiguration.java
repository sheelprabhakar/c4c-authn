package com.c4c.authn.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager", basePackages = {"com.c4c.authn.core.repository"})
public class DynamicDataSourceConfiguration {
    @Value("${spring.datasource.url}")
    private String primaryUrl;

    @Value("${spring.datasource-replica.url}")
    private String replicaUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.jpa.show-sql}")
    private boolean showSql;

    @Value("${spring.jpa.generate-ddl}")
    private boolean generateDdl;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    @Bean
    public DataSource readWriteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(this.primaryUrl);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClassName(this.driverClassName);
        return connectionPoolDataSource(dataSource);
    }

    @Bean
    public DataSource readOnlyDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if(!this.replicaUrl.equals("none")) {
            dataSource.setUrl(this.replicaUrl);
        }else {
            dataSource.setUrl(this.primaryUrl);
        }
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClassName(this.driverClassName);
        return connectionPoolDataSource(dataSource);
    }

    @Bean("dataSource")
    public LazyConnectionDataSourceProxy dataSourceProxy() {
        LazyConnectionDataSourceProxy sourceProxy = new LazyConnectionDataSourceProxy();
        sourceProxy.setReadOnlyDataSource(readOnlyDataSource());
        sourceProxy.setTargetDataSource(readWriteDataSource());

        return sourceProxy;
    }

    protected HikariConfig hikariConfig(
            DataSource dataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        hikariConfig.setMaximumPoolSize(cpuCores * 4);
        hikariConfig.setDataSource(dataSource);

        hikariConfig.setAutoCommit(false);
        return hikariConfig;
    }

    protected HikariDataSource connectionPoolDataSource(
            DataSource dataSource) {
        return new HikariDataSource(hikariConfig(dataSource));
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform(this.hibernateDialect);
        adapter.setDatabase(org.springframework.orm.jpa.vendor.Database.MYSQL);
        adapter.setShowSql(this.showSql);
        adapter.setGenerateDdl(this.generateDdl);
        return adapter;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.c4c.authn.core.entity");

        em.setJpaVendorAdapter(hibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", this.ddlAuto);
        em.setJpaProperties(properties);
        return em;
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager contentTransactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
