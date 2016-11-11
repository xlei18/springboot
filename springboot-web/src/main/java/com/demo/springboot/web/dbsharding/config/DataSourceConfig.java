package com.demo.springboot.web.dbsharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.demo.springboot.web.dbsharding.datasource.DynamicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * DataSourceConfig
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/7 10:02
 */
@Configuration
public class DataSourceConfig implements ApplicationContextAware, InitializingBean{

    private ApplicationContext context;

    @Autowired
    private DruidDataSourceConfig druidDataSourceConfig;

    @Autowired
    private PoolConfig poolConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        registerBeanDefinitions(beanDefinitonRegistry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void registerBeanDefinitions(BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, DruidConfig> datasources = druidDataSourceConfig.getDatasources();
        for (Map.Entry<String, DruidConfig> ds : datasources.entrySet()) {
            String beanName = String.format("%sDataSource", ds.getKey());
            DruidConfig druidConfig = ds.getValue();
            BeanDefinition beanDefinition = BeanDefinitionBuilder
                    .genericBeanDefinition(DruidDataSource.class).getBeanDefinition();
            MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
            mutablePropertyValues.add("url", druidConfig.getUrl());
            mutablePropertyValues.add("username", druidConfig.getUsername());
            mutablePropertyValues.add("password", druidConfig.getPassword());
            mutablePropertyValues.add("driverClassName", druidConfig.getDriverClassName());

            mutablePropertyValues.add("initialSize", poolConfig.getInitialSize());
            mutablePropertyValues.add("maxActive", poolConfig.getMaxActive());
            mutablePropertyValues.add("minIdle", poolConfig.getMinIdle());
            mutablePropertyValues.add("maxWait", poolConfig.getMaxWait());
            mutablePropertyValues.add("testOnBorrow", poolConfig.isTestOnBorrow());
            mutablePropertyValues.add("testOnReturn", poolConfig.isTestOnReturn());
            mutablePropertyValues.add("testWhileIdle", poolConfig.isTestWhileIdle());
            mutablePropertyValues.add("timeBetweenEvictionRunsMillis", poolConfig.getTimeBetweenEvictionRunsMillis());
            mutablePropertyValues.add("minEvictableIdleTimeMillis", poolConfig.getMinEvictableIdleTimeMillis());
            mutablePropertyValues.add("poolPreparedStatements", poolConfig.isPoolPreparedStatements());
            mutablePropertyValues.add("maxPoolPreparedStatementPerConnectionSize",
                    poolConfig.getMaxPoolPreparedStatementPerConnectionSize());
            mutablePropertyValues.add("filters", poolConfig.getFilters());
            mutablePropertyValues.add("connectionProperties", poolConfig.getConnectionProperties());
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "spring")
    public DruidDataSourceConfig druidDataSourceConfig() {
        return new DruidDataSourceConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.pool")
    public PoolConfig poolConfig() {
        return new PoolConfig();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DruidDataSourceConfig druidDataSourceConfig,
                                        PoolConfig poolConfig) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        Map<String, DruidConfig> datasources = druidDataSourceConfig.getDatasources();

        DruidDataSource defaultDataSource = null;
        for (Map.Entry<String, DruidConfig> ds : datasources.entrySet()) {
            DruidConfig druidConfig = ds.getValue();
            DruidDataSource druidDataSource = newDruidDataSource(poolConfig, druidConfig);
            targetDataSources.put(ds.getKey(), druidDataSource);
            if(defaultDataSource == null && druidConfig.isDefault()){
                defaultDataSource = druidDataSource;
            }
        }

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        if(defaultDataSource != null){
            dataSource.setDefaultTargetDataSource(defaultDataSource);
        }
        return dataSource;
    }

    private DruidDataSource newDruidDataSource(PoolConfig poolConfig, DruidConfig druidConfig) {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(druidConfig.getUrl());
        datasource.setUsername(druidConfig.getUsername());
        datasource.setPassword(druidConfig.getPassword());
        datasource.setDriverClassName(druidConfig.getDriverClassName());

        //configuration
        datasource.setInitialSize(poolConfig.getInitialSize());
        datasource.setMinIdle(poolConfig.getMinIdle());
        datasource.setMaxActive(poolConfig.getMaxActive());
        datasource.setMaxWait(poolConfig.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(poolConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTimeMillis());
        datasource.setTestOnBorrow(poolConfig.isTestOnBorrow());
        datasource.setTestOnReturn(poolConfig.isTestOnReturn());
        datasource.setTestWhileIdle(poolConfig.isTestWhileIdle());
        datasource.setValidationQuery(poolConfig.getValidationQuery());
        datasource.setPoolPreparedStatements(poolConfig.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(poolConfig
                .getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(poolConfig.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        datasource.setConnectionProperties(poolConfig.getConnectionProperties());

        return datasource;
    }
}
