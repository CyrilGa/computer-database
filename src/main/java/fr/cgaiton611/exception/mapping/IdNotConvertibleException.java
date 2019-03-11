package fr.cgaiton611.exception.mapping;

public class IdNotConvertibleException extends MappingException{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Id is not convertible, bad format";
	}

}
