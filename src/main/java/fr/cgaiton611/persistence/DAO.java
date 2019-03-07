package fr.cgaiton611.persistence;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Abstract Class getting the connection with the database and define CRUD
 * method to be implemented
 * 
 * @author cyril
 * @version 1.0
 * @param <T>
 */

//@Repository
public abstract class DAO<T> {

//	@Autowired
	private ConnectionDatabase connectionDatabase = new ConnectionDatabase();
	public HikariDataSource ds = connectionDatabase.getDataSource() ;

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
