package fr.cgaiton611;


import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaxxer.hikari.HikariDataSource;

import fr.cgaiton611.persistence.ConnectionDatabase;
import fr.cgaiton611.springconfig.SpringConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class ConnectionDatabaseTest {
	
	@Autowired
	ConnectionDatabase connectionDatabase;

	@Test
	public void creation() {
		HikariDataSource ds = connectionDatabase.getDataSource();
		assertNotEquals(ds, null);
		
	}

}
