package fr.cgaiton611.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton Create the connection with the database with the given constants
 * and return an instance of it
 * 
 * @author cyril
 * @version 1.0
 */

@Repository
public class ConnectionDatabase {
	private final Logger logger = LoggerFactory.getLogger(ConnectionDatabase.class);
	private final String configFile = "resources/db/db.properties";
	private HikariConfig config;
	private HikariDataSource ds;


	public ConnectionDatabase() {
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
	}

	public HikariDataSource getDataSource() {
		return ds;
	}
}
