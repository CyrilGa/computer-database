package fr.cgaiton611.cdb.exception;

public class UpdateException extends DAOException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The update encounter a problem";
	}
}
