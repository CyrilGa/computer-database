package fr.cgaiton611.exception.dao;

public class NoResultRowException extends DAOException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No result row returned after the query";
	}
	
}
