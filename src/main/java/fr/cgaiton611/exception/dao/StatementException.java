package fr.cgaiton611.exception.dao;

public class StatementException extends DAOException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The database request failed";
	}

}
