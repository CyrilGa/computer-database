package fr.cgaiton611.cdb.exception;

public class NameIsEmptyException extends ValidationException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Name must not be empty (default value)";
	}
}
