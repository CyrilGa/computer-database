package fr.cgaiton611.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.persistence.ConnectionDatabase;

class ConnectionDatabaseTest {

	@Test
	void creation() {
		Connection connection = ConnectionDatabase.getInstance();
		assertNotEquals(connection, null);
	}

}
