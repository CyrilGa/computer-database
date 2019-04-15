package fr.cgaiton611.cdb.exception.entityValidation;

public class NumPageNotValidException extends EntityValidationException {

	private static final long serialVersionUID = 1L;

	private int min, max;
	
	public NumPageNotValidException(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public String getMessage() {
		return "In this case, the value of the attribute \"nbPage\" should be between " + min + " and " + max;
	}

}
