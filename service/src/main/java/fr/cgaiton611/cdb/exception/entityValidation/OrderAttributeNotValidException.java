package fr.cgaiton611.cdb.exception.entityValidation;

public class OrderAttributeNotValidException extends EntityValidationException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The value of the attribute orderAttribute should be contained in (\"id\", \"computerName\", \"introduced\""
				+ ", \"discontinued\", \"companyName\").";
	}

}
