package fr.cgaiton611.service;

import java.util.List;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;

public class CompanyService {
	CompanyDAO companyDAO = new CompanyDAO();

	public List<Company> findPaged(int page) {
		int elements = 15;
		return companyDAO.findPaged(page, elements);
	}
}
