package fr.cgaiton611.exception.dao;

public class NotOneResultException extends DAOException{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "The Query doesn't return one and only one result";
	}
}
