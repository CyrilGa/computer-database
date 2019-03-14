package fr.cgaiton611.exception.dao;

public class UpdateException extends DAOException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The update encounter a problem";
	}
}
