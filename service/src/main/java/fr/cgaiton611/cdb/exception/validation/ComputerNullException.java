package fr.cgaiton611.cdb.exception.validation;

public class ComputerNullException extends ValidationException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Computer must not be null";
	}
}
