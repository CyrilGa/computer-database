package fr.cgaiton611.persistence;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.cgaiton611.exception.DAOException;


/**
 * Abstract Class getting the connection with the database and define CRUD
 * method to be implemented
 * 
 * @author cyril
 * @version 1.0
 * @param <T>
 */

@Repository
public abstract class DAO<T> {

	@Autowired
	protected DataSource ds;

	/**
	 * Create an object in the database
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Optional<T> create(T obj) throws DAOException;

	/**
	 * Find an object in the database
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Optional<T> find(T obj) throws DAOException;

	/**
	 * Update an object in the database
	 * 
	 * @param obj
	 * @return
	 */
	public abstract Optional<T> update(T obj) throws DAOException;

	/**
	 * Delete an object in the database
	 * 
	 * @param obj
	 */
	public abstract void delete(T obj) throws DAOException;

}
