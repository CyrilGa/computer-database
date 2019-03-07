package fr.cgaiton611.springconfig;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.cgaiton611.persistence.ConnectionDatabase;


@Configuration
@ComponentScan(basePackages= {
		"fr.cgaiton611.model",
		"fr.cgaiton611.persistence",
		"fr.cgaiton611.service",
		"fr.cgaiton611.dto",
		"fr.cgaiton611.cli"})
public class SpringConfig {
	private final Logger logger = LoggerFactory.getLogger(ConnectionDatabase.class);
	private final String configFile = "src/main/resources/db/db.properties";
	
	@Bean
    public DataSource dataSource(){
		DataSource ds = null;
		HikariConfig config = null;
		try {
			config = new HikariConfig(configFile);
			try {
				ds = new HikariDataSource(config);
				logger.info("Datasource initialized");
			} catch (Exception e) {
				logger.error("Error initializing HikariDataSource");
			}
		} catch (RuntimeException e) {
			logger.error("Properties file for database not found or incorrect");
		}
        return ds;
    }
}
