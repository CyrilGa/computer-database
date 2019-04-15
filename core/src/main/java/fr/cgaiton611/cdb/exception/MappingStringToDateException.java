package fr.cgaiton611.cdb.exception;

public class MappingStringToDateException extends MappingException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "An error occured when trying to convert string to Date";
	}
}
