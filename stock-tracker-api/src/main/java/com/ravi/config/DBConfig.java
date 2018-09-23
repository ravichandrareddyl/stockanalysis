package com.ravi.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class DBConfig {

    @Value("${db.username}")
    private String userName;

    @Value("${db.password}")
    private String password;

    @Value("${db.host}")
    private String host;

    @Value("${db.schema}")
    private String schema;


    @Bean(name = "dsStk")
    public DataSource dsStk() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s?useSSL=false", host, schema));
        config.setUsername(userName);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("maximumPoolSize", "10");

        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
} 