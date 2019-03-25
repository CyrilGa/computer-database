package fr.cgaiton611.cdb.exception;

public class NoRowUpdatedException extends DAOException {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No row updated after the update";
	}
}
