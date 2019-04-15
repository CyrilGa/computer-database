package fr.cgaiton611.cdb.exception;

public class MappingException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Error in the mapping";
	}
	
}
