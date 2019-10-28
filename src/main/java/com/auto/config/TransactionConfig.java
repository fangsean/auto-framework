package com.auto.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-05-20 .
 * Update Date Time:
 */
@Configuration
@Lazy
@MapperScan("com.auto.service")
@EnableTransactionManagement(order = 2)
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager txManager(com.alibaba.druid.pool.DruidDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        dataSourceTransactionManager.setGlobalRollbackOnParticipationFailure(false);
        return dataSourceTransactionManager;
    }
}