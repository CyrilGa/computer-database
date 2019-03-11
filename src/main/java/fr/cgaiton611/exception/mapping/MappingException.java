package fr.cgaiton611.exception.mapping;

public class MappingException extends Exception{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Mapping exception";
	}
}
