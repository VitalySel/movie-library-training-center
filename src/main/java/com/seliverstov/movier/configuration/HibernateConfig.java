package com.seliverstov.movier.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

public class HibernateConfig {

    @Configuration
    public class MainConfig {

        @Bean
        public DataSource dataSource() throws URISyntaxException {
            URI dbUri = new URI(System.getenv("CLEARDB DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

            DriverManagerDataSource basicDataSource = new DriverManagerDataSource();
            basicDataSource.setUrl(dbUrl);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);

            return basicDataSource;
        }
    }
}
