package com.zt.homework.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zt.homework.dao")
public class dataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;


    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        // 关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);

        // 最大空闲时间，60秒内未使用则连接被丢弃。若为0则永不丢弃。默认值: 0
        dataSource.setMaxIdleTime(60);
        // 当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间，
        //超时后将抛出SQLException，如设为0则无限期等待。单位毫秒。默认: 0
        dataSource.setCheckoutTimeout(3000);
        // c3p0将建一张名为Test的空表，并使用其自带的查询语句进行测试。
        //如果定义了这个参数那么属性preferredTestQuery将被忽略。
        //你不能在这张Test表上进行任何操作，它将只供c3p0测试使用。默认值: null
        dataSource.setAutomaticTestTable("Test");
        // 因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
        //　　时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
        //　　等方法来提升连接测试的性能。Default: false
        dataSource.setTestConnectionOnCheckout(false);
        // 如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
        dataSource.setTestConnectionOnCheckin(true);
        // 每60秒检查所有连接池中的空闲连接。Default: 0
        dataSource.setIdleConnectionTestPeriod(60);

        return dataSource;
    }

}
