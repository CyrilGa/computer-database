package fr.cgaiton611.exception.dao;

public class QueryException extends DAOException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The query encounter a problem";
	}
}
