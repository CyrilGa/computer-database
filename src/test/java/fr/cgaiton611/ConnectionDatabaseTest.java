package fr.cgaiton611;


import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.zaxxer.hikari.HikariDataSource;

import fr.cgaiton611.persistence.ConnectionDatabase;

class ConnectionDatabaseTest {

	@Test
	void creation() {
		HikariDataSource ds = ConnectionDatabase.getDataSource();
		assertNotEquals(ds, null);
		
	}

}
