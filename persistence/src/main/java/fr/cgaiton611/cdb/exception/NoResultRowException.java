package fr.cgaiton611.cdb.exception;

public class NoResultRowException extends DAOException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No result row returned after the query";
	}
	
}
