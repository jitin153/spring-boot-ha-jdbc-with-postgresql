package com.hajdbc.demo.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource({ "classpath:dbcp-config.properties" })
public class DataSourceConfig {
	@Bean
	@ConfigurationProperties("app.datasource")
	public DataSource dataSource() throws SQLException {
		return new HikariDataSource();
	}
}
