package com.auto.config.properties;

import lombok.Data;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidSettings {

    /**
     * 密码解码器自动注入
     */
    @Autowired
    StringEncryptor stringEncryptor;

    /**
     * 连接池初始化连接数量
     */
    public final static int DEFAULT_INITIAL_SIZE = 0;
    /**
     * 最大的活动数量
     */
    public final static int DEFAULT_MAX_ACTIVE_SIZE = 50;
    /**
     * 最小空闲数量
     */
    public final static int DEFAULT_MIN_IDLE = 0;
    /**
     * 最大等待时长 默认锁住线程一直等待
     */
    public final static long DEFAULT_MAX_WAIT = -1;
    /**
     * 是否空闲时默认开启测试
     */
    private static final boolean DEFAULT_TEST_WHILE_IDLE = true;
    /**
     * 默认验证语句
     */
    private static final String DEFAULT_VALIDATION_QUERY = "select 1";
    /**
     * 默认慢sql秒数
     */
    private static final long DEFAULT_SLOW_SQL_MILLIS = 1000L;
    private static final Long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60000L;
    private static final Long DEFAULT_MIN_EVICEABLE_IDLE_TIME_MILLIS = 300000L;

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    private Integer initialSize = DEFAULT_INITIAL_SIZE;
    private Integer minIdle = DEFAULT_MIN_IDLE;
    private Integer maxActive = DEFAULT_MAX_ACTIVE_SIZE;

    private Long maxWait = DEFAULT_MAX_WAIT;
    private Long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    private Long minEvictableIdleTimeMillis = DEFAULT_MIN_EVICEABLE_IDLE_TIME_MILLIS;

    private String validationQuery = DEFAULT_VALIDATION_QUERY;
    private boolean testWhileIdle = DEFAULT_TEST_WHILE_IDLE;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean poolPreparedStatements = false;
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;
    private String filters = "config,stat,log4j";
    private String connectionProperties = "config.decrypt=false";
    private long slowSqlMillis = DEFAULT_SLOW_SQL_MILLIS;

}
