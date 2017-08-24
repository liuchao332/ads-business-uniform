package com.iflytek.ads.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Druid配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-21 0:00
 */
@Configuration
@MapperScan(basePackages = DruidConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DruidConfig {
    private Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "io.renren.dao.core";
    static final String MAPPER_LOCATION = "classpath:mapper/core/**/*.xml";

    @Autowired
    DatabaseConfig databaseConfig;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource(){
        DataSource dataSource = databaseConfig.dataSource(BaseName.CORE);
        return dataSource;
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setConfigLocation(databaseConfig.getConfigLocation());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DruidConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
