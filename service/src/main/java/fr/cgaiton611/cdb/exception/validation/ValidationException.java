package fr.cgaiton611.cdb.exception.validation;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Validation exception";
	}

}
