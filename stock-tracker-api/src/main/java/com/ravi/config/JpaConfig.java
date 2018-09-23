package com.ravi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static java.util.Collections.singletonMap;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "sqlEntityManagerFactory",
        transactionManagerRef = "sqlTransactionManager",
        basePackages = "com.ravi.repository"
)
@EnableTransactionManagement
public class JpaConfig {

    @Primary
    @Bean(name = "sqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                            final @Qualifier("dsStk") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.ravi.domain")
                .persistenceUnit("userMng")
                .properties(singletonMap("hibernate.hbm2ddl.auto", "none"))
                .build();
    }

    @Primary
    @Bean(name = "sqlTransactionManager")
    public PlatformTransactionManager sqlTransactionManager(@Qualifier("sqlEntityManagerFactory")
                                                              EntityManagerFactory sqlEntityManagerFactory) {
        return new JpaTransactionManager(sqlEntityManagerFactory);
    }
}