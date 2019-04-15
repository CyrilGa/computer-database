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
		return "The number of elements should be between " + min + " and " + max;
	}

}
