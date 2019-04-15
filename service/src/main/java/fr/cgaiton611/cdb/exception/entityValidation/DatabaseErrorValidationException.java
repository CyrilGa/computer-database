package fr.cgaiton611.cdb.exception.entityValidation;

public class DatabaseErrorValidationException extends EntityValidationException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Error in databse durring the validation";
	}
	
}
