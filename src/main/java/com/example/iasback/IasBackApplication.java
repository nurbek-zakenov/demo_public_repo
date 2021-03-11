package com.example.iasback;


import com.example.iasback.property.FileStorageProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.sql.DataSource;
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication(scanBasePackages = {"com.example.iasback"})
public class IasBackApplication {


	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setIncludeClientInfo(true);
		filter.setMaxPayloadLength(10000);
		filter.setAfterMessagePrefix("After request: ");
		return filter;
	}

    @Primary
    @Bean(name = "simpleDataSource")
    public DataSource simpleDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/ias_db");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setMaxLifetime(2000000);
        hikariConfig.setPoolName("SpringBootJPAHikariCP");
        return new HikariDataSource(hikariConfig);
    }


    @Bean()
    public MapperScannerConfigurer simpleMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.example.iasback.repository");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("simpleSqlSessionFactory");
        return mapperScannerConfigurer;
    }


    @Bean
    public SqlSessionFactory simpleSqlSessionFactory(@Qualifier("simpleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setFailFast(true);
        return sessionFactory.getObject();
    }




    @Bean(name ="netDataSource")
    public DataSource netDatasource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/ias_db2");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setMaxLifetime(2000000);
        hikariConfig.setPoolName("SpringBootJPAHikariCP");
        return new HikariDataSource(hikariConfig);
    }




    @Bean()
    public MapperScannerConfigurer netMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.example.iasback.netrepository");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("netSqlSessionFactory");
        return mapperScannerConfigurer;
    }


    @Bean
    public SqlSessionFactory netSqlSessionFactory(@Qualifier("netDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setFailFast(true);
        return sessionFactory.getObject();
    }

	public static void main(String[] args) {
		SpringApplication.run(IasBackApplication.class, args);
	}

}
