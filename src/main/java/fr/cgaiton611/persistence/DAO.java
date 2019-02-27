package fr.cgaiton611.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Abstract Class getting the connection with the database and define CRUD
 * method to be implemented
 * 
 * @author cyril
 * @version 1.0
 * @param <T>
 */
public abstract class DAO<T> {

	public Connection connection;
	
	public DAO() {
		try {
			connection = ConnectionDatabase.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create an object in the database
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Optional<T> create(T obj);

	/**
	 * Find an object in the database
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Optional<T> find(T obj);

	/**
	 * Update an object in the database
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Optional<T> update(T obj);

	/**
	 * Delete an object in the database
	 * 
	 * @param obj
	 */
	public abstract void delete(T obj);

}
