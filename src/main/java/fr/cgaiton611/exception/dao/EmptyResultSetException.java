package fr.cgaiton611.exception.dao;

public class EmptyResultSetException extends DAOException{
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Empty result set";
	}
}
