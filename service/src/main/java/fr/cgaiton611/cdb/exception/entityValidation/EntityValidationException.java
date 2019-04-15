package fr.cgaiton611.cdb.exception.entityValidation;

public class EntityValidationException extends Exception{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Error in entity validation";
	}
	
}
