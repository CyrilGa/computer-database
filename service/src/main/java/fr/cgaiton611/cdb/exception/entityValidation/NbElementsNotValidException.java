package fr.cgaiton611.cdb.exception.entityValidation;

public class NbElementsNotValidException extends EntityValidationException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The value of the attribute \"nbElements\" should be in (10, 25, 50, 75, 100).";
	}
	
}
