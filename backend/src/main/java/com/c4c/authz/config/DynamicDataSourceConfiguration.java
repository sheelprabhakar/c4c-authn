package com.c4c.authz.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
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

/**
 * The type Dynamic data source configuration.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager", basePackages = {"com.c4c.authz.core.repository"})
public class DynamicDataSourceConfiguration {
    /**
     * The constant CPU_COUNT.
     */
    public static final int CPU_COUNT = 4;
    /**
     * The Primary url.
     */
    @Value("${spring.datasource.url}")
    private String primaryUrl;

    /**
     * The Replica url.
     */
    @Value("${spring.datasource-replica.url}")
    private String replicaUrl;

    /**
     * The Username.
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * The Password.
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * The Driver class name.
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * The Show sql.
     */
    @Value("${spring.jpa.show-sql}")
    private boolean showSql;

    /**
     * The Generate ddl.
     */
    @Value("${spring.jpa.generate-ddl}")
    private boolean generateDdl;

    /**
     * The Hibernate dialect.
     */
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    /**
     * The Ddl auto.
     */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    /**
     * Read write data source data source.
     *
     * @return the data source
     */
    @Bean
    public DataSource readWriteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(this.primaryUrl);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClassName(this.driverClassName);
        return connectionPoolDataSource(dataSource);
    }

    /**
     * Read only data source data source.
     *
     * @return the data source
     */
    @Bean
    public DataSource readOnlyDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if (!this.replicaUrl.equals("none")) {
            dataSource.setUrl(this.replicaUrl);
        } else {
            dataSource.setUrl(this.primaryUrl);
        }
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClassName(this.driverClassName);
        return connectionPoolDataSource(dataSource);
    }

    /**
     * Data source proxy lazy connection data source proxy.
     *
     * @return the lazy connection data source proxy
     */
    @Bean("dataSource")
    public LazyConnectionDataSourceProxy dataSourceProxy() {
        LazyConnectionDataSourceProxy sourceProxy = new LazyConnectionDataSourceProxy();
        sourceProxy.setReadOnlyDataSource(readOnlyDataSource());
        sourceProxy.setTargetDataSource(readWriteDataSource());

        return sourceProxy;
    }

    /**
     * Hikari config hikari config.
     *
     * @param dataSource the data source
     * @return the hikari config
     */
    protected HikariConfig hikariConfig(
            final DataSource dataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        hikariConfig.setMaximumPoolSize(cpuCores * CPU_COUNT);
        hikariConfig.setDataSource(dataSource);

        hikariConfig.setAutoCommit(false);
        return hikariConfig;
    }

    /**
     * Connection pool data source hikari data source.
     *
     * @param dataSource the data source
     * @return the hikari data source
     */
    protected HikariDataSource connectionPoolDataSource(
            final DataSource dataSource) {
        return new HikariDataSource(hikariConfig(dataSource));
    }

    /**
     * Hibernate jpa vendor adapter hibernate jpa vendor adapter.
     *
     * @return the hibernate jpa vendor adapter
     */
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform(this.hibernateDialect);
        adapter.setDatabase(org.springframework.orm.jpa.vendor.Database.MYSQL);
        adapter.setShowSql(this.showSql);
        adapter.setGenerateDdl(this.generateDdl);
        return adapter;
    }

    /**
     * Entity manager factory local container entity manager factory bean.
     *
     * @param dataSource the data source
     * @return the local container entity manager factory bean
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") final DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.c4c.authz.core.entity");

        em.setJpaVendorAdapter(hibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", this.ddlAuto);
        em.setJpaProperties(properties);
        return em;
    }


    /**
     * Content transaction manager platform transaction manager.
     *
     * @param entityManagerFactory the entity manager factory
     * @return the platform transaction manager
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager contentTransactionManager(
            @Qualifier("entityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
