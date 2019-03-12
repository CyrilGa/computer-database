package fr.cgaiton611.exception.dao;

public class DataSourceException extends DAOException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "DataSource not accessible";
	}

}
