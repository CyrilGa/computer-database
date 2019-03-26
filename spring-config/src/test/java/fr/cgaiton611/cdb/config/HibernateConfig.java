package fr.cgaiton611.cdb.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

import fr.cgaiton611.cdb.config.HibernateConfig;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "fr.cgaiton611.cdb.page", "fr.cgaiton611.cdb.persistence", "fr.cgaiton611.cdb.service",
		"fr.cgaiton611.cdb.dto", "fr.cgaiton611.cdb.cli", "fr.cgaiton611.cdb.controller" })
public class HibernateConfig {

	private final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
	private final String configFileMain = "src/main/resources/db/main/db.properties";

	@Bean
	public DataSource dataSource() {
		DataSource ds = new HikariDataSource();
		HikariConfig config = null;
		try {
			config = new HikariConfig(configFileMain);
			try {
				ds = new HikariDataSource(config);
				logger.info("Datasource initialized (test db)");
			} catch (PoolInitializationException e) {
				logger.error("Error initializing HikariDataSource");
			}
		} catch (RuntimeException e) {
			logger.error("Properties file for database not found or incorrect");
		}
		return ds;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("fr.cgaiton611.cdb.model");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
		return hibernateProperties;
	}

}