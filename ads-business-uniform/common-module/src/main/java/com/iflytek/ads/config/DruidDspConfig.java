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
@MapperScan(basePackages = DruidDspConfig.PACKAGE, sqlSessionFactoryRef = "dspSqlSessionFactory")
public class DruidDspConfig {
    private Logger logger = LoggerFactory.getLogger(DruidDspConfig.class);

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "io.renren.dao.dsp";
    static final String MAPPER_LOCATION = "classpath:mapper/dsp/*.xml";

    @Autowired
    DatabaseConfig databaseConfig;

    @Bean(name = "dspDataSource")
    public DataSource dspDataSource(){
        DataSource dataSource = databaseConfig.dataSource(BaseName.DSP);
        return dataSource;
    }

    @Bean(name = "dspTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dspDataSource());
    }

    @Bean(name = "dspSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dspDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation(databaseConfig.getConfigLocation());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DruidDspConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
