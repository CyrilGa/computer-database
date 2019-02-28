package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.persistence.ConnectionDatabase;

class ConnectionDatabaseTest {

	@Test
	void creation() {
		Connection connection = null;
		try {
			connection = ConnectionDatabase.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotEquals(connection, null);
	}

}
