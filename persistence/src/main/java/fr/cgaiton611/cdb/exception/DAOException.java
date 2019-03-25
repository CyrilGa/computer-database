package fr.cgaiton611.cdb.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "DAO exception";
	}

}
