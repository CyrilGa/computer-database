package fr.cgaiton611.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;

@Service
public class CompanyService {
	@Autowired
	CompanyDAO companyDAO;

	public List<Company> findPaged(int page, int elements) {
		return companyDAO.findPaged(page, elements);
	}
	
	public Optional<Company> find(long id) {
		return companyDAO.find(new Company(id));
	}
	
	public Optional<Company> findByName(String name) {
		return companyDAO.findByName(name);
	}
	
	public int count() {
		return companyDAO.count();
	}
	
	public List<String> findAllName(){
		return companyDAO.findAllName();
	}
	
	public void delete(long id) {
		companyDAO.delete(new Company(id));
	}
}
