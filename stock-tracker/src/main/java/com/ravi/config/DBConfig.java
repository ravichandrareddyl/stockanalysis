package com.ravi.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;




@Configuration
public class DBConfig {

    @Bean(name = "dsStk")
    @ConfigurationProperties(prefix = "spring.stkdb") 
    public DataSource dsStk() {
        return DataSourceBuilder.create().build();
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