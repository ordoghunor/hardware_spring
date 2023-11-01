package edu.bbte.idde.ohim2065.hardware.backend.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.ohim2065.hardware.backend.config.Config;
import edu.bbte.idde.ohim2065.hardware.backend.config.ConfigFactory;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            Config config = ConfigFactory.getConfig();

            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setJdbcUrl(config.getUrl());
            hikariConfig.setUsername(config.getUser());
            hikariConfig.setPassword(config.getPassword());
            hikariConfig.setDriverClassName(config.getDriverClassName());

            hikariConfig.setMaximumPoolSize(config.getPoolSize());

            dataSource = new HikariDataSource(hikariConfig);
        }
        return dataSource;
    }

}
