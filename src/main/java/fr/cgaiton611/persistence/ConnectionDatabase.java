package fr.cgaiton611.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton
 * Create the connection with the database with the given constants
 * and return an instance of it
 * @author cyril
 * @version 1.0
 */
public class ConnectionDatabase {
	private final static String url = "jdbc:mysql://localhost/computer-database-db";
	private final static String user = "admincdb";
	private final static String password = "qwerty1234";
	
	private static Connection connection;
	
	/**
	 * Initialize the connection with the database
	 * then, return the created instance
	 * @return
	 */
	public static Connection getInstance(){
	    if (connection == null) {
	        try {
	            connection = DriverManager.getConnection(url, user, password);
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    return connection;
	}
}