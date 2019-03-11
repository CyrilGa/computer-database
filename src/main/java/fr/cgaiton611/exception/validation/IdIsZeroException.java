package fr.cgaiton611.exception.validation;

public class IdIsZeroException extends ValidationException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Id must not be 0 (default value)";
	}
}
