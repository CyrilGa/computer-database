package fr.cgaiton611.cdb.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:db-main.properties" })
public class HibernateConfig {

	private final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);

	@Autowired
	private Environment env;

	public HibernateConfig() {
		logger.info("##### HibernateConfig is being initialized ... #####");
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		try {
			ds.setJdbcUrl(env.getRequiredProperty("jdbcUrl"));
			ds.setUsername(env.getRequiredProperty("dataSource.user"));
			ds.setPassword(env.getRequiredProperty("dataSource.password"));
			logger.info("Datasource initialized (main db)");
		} catch (PoolInitializationException e) {
			logger.error("Error initializing HikariDataSource");
		} catch (IllegalStateException e) {
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
