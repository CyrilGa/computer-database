package fr.cgaiton611.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton Create the connection with the database with the given constants
 * and return an instance of it
 * 
 * @author cyril
 * @version 1.0
 */
public class ConnectionDatabase {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionDatabase.class);
	private final static String configFile = "resources/db/db.properties";
	private static HikariConfig config;
	private static HikariDataSource ds;

	static {
		try {
			config = new HikariConfig(configFile);
		} catch (Exception e) {
			logger.error("Properties file for database not found");
		}
		try {
			ds = new HikariDataSource(config);
		} catch (Exception e) {
			logger.error("Error initializing HikariDataSource");
		}
	}

	private ConnectionDatabase() {
	}

	public static HikariDataSource getDataSource() {
		return ds;
	}
}
