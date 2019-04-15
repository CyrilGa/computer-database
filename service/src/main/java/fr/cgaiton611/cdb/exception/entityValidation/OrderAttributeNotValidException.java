package fr.cgaiton611.cdb.exception.entityValidation;

public class OrderAttributeNotValidException extends EntityValidationException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The number of elements should be in (\"id\", \"computerName\", \"introduced\",\n" + 
				"			\"discontinued\", \"companyName\").";
	}
	
}
