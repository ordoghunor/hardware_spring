package edu.bbte.idde.ohim2065.hardware.spring.dao.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("jdbc")
public class DataSourceConfiguration {
    @Value("${jdbc.driverClass}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.userName}")
    private String userName;
    @Value("${jdbc.passWord}")
    private String passWord;
    @Value("${jdbc.poolSize}")
    private Integer poolSize;

    @Bean
    public DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClass);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(userName);
        hikariDataSource.setPassword(passWord);
        hikariDataSource.setMaximumPoolSize(poolSize);
        return hikariDataSource;
    }
}
