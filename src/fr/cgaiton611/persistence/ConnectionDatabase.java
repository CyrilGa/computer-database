package fr.cgaiton611.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
	private static String url = "jdbc:mysql://localhost/computer-database-db";
	private static String user = "admincdb";
	private static String password = "qwerty1234";
	
	private static Connection connection;
	
	
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