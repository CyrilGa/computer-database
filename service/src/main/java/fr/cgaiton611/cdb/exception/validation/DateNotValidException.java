package fr.cgaiton611.cdb.exception.validation;

public class DateNotValidException extends ValidationException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Date must be between 0000-01-01 00:00 and 9999-12-31 23:59";
	}
}
