package fr.cgaiton611.springconfig;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

@Configuration
@ComponentScan(basePackages = { "fr.cgaiton611.page", "fr.cgaiton611.persistence", "fr.cgaiton611.service",
		"fr.cgaiton611.dto", "fr.cgaiton611.cli" })
public class SpringConfig {
	private final Logger logger = LoggerFactory.getLogger(SpringConfig.class);
	private final String configFile = "src/main/resources/db/db.properties";

	@Bean
	public DataSource dataSource() {
		DataSource ds = new HikariDataSource();
		HikariConfig config = null;
		try {
			config = new HikariConfig(configFile);
			try {
				ds = new HikariDataSource(config);
				logger.info("Datasource initialized");
			} catch (PoolInitializationException e) {
				logger.error("Error initializing HikariDataSource");
			}
		} catch (RuntimeException e) {
			logger.error("Properties file for database not found or incorrect");
		}
		return ds;
	}

}
