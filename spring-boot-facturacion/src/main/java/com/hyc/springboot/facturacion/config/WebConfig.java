package com.hyc.springboot.facturacion.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class WebConfig {

	@Bean(name = "db")
	//@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		/*return DataSourceBuilder.create().build();*/
		
		 return DataSourceBuilder.create()
        .driverClassName("org.postgresql.Driver")
        .url("jdbc:postgresql://localhost:5432/facturacion_sp")
        .username("postgres")
        .password("pgadmin").build();
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("db") DataSource ds) {
		return new JdbcTemplate(ds);
	}
}
