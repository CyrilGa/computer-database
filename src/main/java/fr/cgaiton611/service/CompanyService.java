package fr.cgaiton611.service;

import java.util.List;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;

public class CompanyService {
	CompanyDAO companyDAO = CompanyDAO.getInstance();
	
	private static CompanyService instance = new CompanyService();
	
	private CompanyService() {};
	
	public static CompanyService getInstance() {
		return instance;
	}
	

	public List<Company> findPaged(int page, int elements) {
		return companyDAO.findPaged(page, elements);
	}
	
	public int count() {
		return companyDAO.count();
	}
}
