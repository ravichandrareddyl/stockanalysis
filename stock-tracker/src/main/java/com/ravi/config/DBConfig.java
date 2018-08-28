package com.ravi.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;




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

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource") 
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    // @Bean(name = "dsUK")
    // @ConfigurationProperties(prefix = "spring.dsuk") 
    // public DataSource dsUK() {
    //     return DataSourceBuilder.create().build();
    // }

    // @Bean(name = "dsMV")
    // @ConfigurationProperties(prefix = "spring.dsmv") 
    // public DataSource dsMV() {
    //     return DataSourceBuilder.create().build();
    // }

    // @Bean(name = "jdbcMap") 
    // public Map<String, JdbcTemplate> getTemplateMap(@Qualifier("dsUS") DataSource dsUS, 
    //                 @Qualifier("dsUK") DataSource dsUK,
    //                 @Qualifier("dsMV") DataSource dsMV) {
    //     Map<String, JdbcTemplate> map = new HashMap<String, JdbcTemplate>();
    //     map.put("US", new JdbcTemplate(dsUS));
    //     map.put("GB", new JdbcTemplate(dsUK));
    //     map.put("MV", new JdbcTemplate(dsMV));

    //     return map; 
    // }

} 