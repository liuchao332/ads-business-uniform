package com.iflytek.ads.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Configuration
public class DatabaseConfig {

    @Value("classpath:mybatis.xml")
    private Resource configLocation;

    @Value("${spring.datasource.core.url:#{null}}")
    private String dbUrl;

    @Value("${spring.datasource.core.username: #{null}}")
    private String username;

    @Value("${spring.datasource.core.password:#{null}}")
    private String password;

    @Value("${spring.datasource.core.driverClassName:#{null}}")
    private String driverClassName;

    @Value("${spring.datasource.pool.initialSize:#{null}}")
    private Integer initialSize;

    @Value("${spring.datasource.pool.minIdle:#{null}}")
    private Integer minIdle;

    @Value("${spring.datasource.pool.maxActive:#{null}}")
    private Integer maxActive;

    @Value("${spring.datasource.pool.maxWait:#{null}}")
    private Integer maxWait;

    @Value("${spring.datasource.pool.timeBetweenEvictionRunsMillis:#{null}}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.pool.minEvictableIdleTimeMillis:#{null}}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${spring.datasource.pool.validationQuery:#{null}}")
    private String validationQuery;

    @Value("${spring.datasource.pool.testWhileIdle:#{null}}")
    private Boolean testWhileIdle;

    @Value("${spring.datasource.pool.testOnBorrow:#{null}}")
    private Boolean testOnBorrow;

    @Value("${spring.datasource.pool.testOnReturn:#{null}}")
    private Boolean testOnReturn;

    @Value("${spring.datasource.pool.poolPreparedStatements:#{null}}")
    private Boolean poolPreparedStatements;

    @Value("${spring.datasource.pool.maxPoolPreparedStatementPerConnectionSize:#{null}}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.pool.filters:#{null}}")
    private String filters;

    @Value("{spring.datasource.pool.connectionProperties:#{null}}")
    private String connectionProperties;

    //dsp数据库配置
    @Value("${spring.datasource.dsp.url:#{null}}")
    private String dspDbUrl;

    @Value("${spring.datasource.dsp.username: #{null}}")
    private String dspUsername;

    @Value("${spring.datasource.dsp.password:#{null}}")
    private String dspPassword;

    @Value("${spring.datasource.dsp.driverClassName:#{null}}")
    private String dspDriverClassName;

    public Resource getConfigLocation() {
        return configLocation;
    }

    public DataSource dataSource(BaseName base){
        DruidDataSource datasource = new DruidDataSource();
        switch (base){
            case CORE:
                datasource.setUrl(this.dbUrl);
                datasource.setUsername(this.username);
                datasource.setPassword(this.password);
                datasource.setDriverClassName(this.driverClassName);
                break;
            case DSP:
                datasource.setUrl(this.dspDbUrl);
                datasource.setUsername(this.dspUsername);
                datasource.setPassword(this.dspPassword);
                datasource.setDriverClassName(this.dspDriverClassName);
                break;
            default:
                break;
        }
        if(initialSize != null) {
            datasource.setInitialSize(initialSize);
        }
        if(minIdle != null) {
            datasource.setMinIdle(minIdle);
        }
        if(maxActive != null) {
            datasource.setMaxActive(maxActive);
        }
        if(maxWait != null) {
            datasource.setMaxWait(maxWait);
        }
        if(timeBetweenEvictionRunsMillis != null) {
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }
        if(minEvictableIdleTimeMillis != null) {
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        if(validationQuery!=null) {
            datasource.setValidationQuery(validationQuery);
        }
        if(testWhileIdle != null) {
            datasource.setTestWhileIdle(testWhileIdle);
        }
        if(testOnBorrow != null) {
            datasource.setTestOnBorrow(testOnBorrow);
        }
        if(testOnReturn != null) {
            datasource.setTestOnReturn(testOnReturn);
        }
        if(poolPreparedStatements != null) {
            datasource.setPoolPreparedStatements(poolPreparedStatements);
        }
        if(maxPoolPreparedStatementPerConnectionSize != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }

        if(connectionProperties != null) {
            datasource.setConnectionProperties(connectionProperties);
        }

        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);
        return datasource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }

    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(1000);

        return statFilter;
    }

    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();

        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);

        return wallFilter;
    }
}
