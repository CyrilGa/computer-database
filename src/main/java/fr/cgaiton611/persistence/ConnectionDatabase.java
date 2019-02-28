package fr.cgaiton611.persistence;

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
	private final static String url = "jdbc:mysql://localhost/computer-database-db";
	private final static String user = "admincdb";
	private final static String password = "qwerty1234";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
 
    static {
        config.setJdbcUrl( url );
        config.setUsername( user );
        config.setPassword( password );
        ds = new HikariDataSource( config );
    }
 
    private ConnectionDatabase() {}
 
    public static HikariDataSource getDataSource() {
        return ds;
    }
}
