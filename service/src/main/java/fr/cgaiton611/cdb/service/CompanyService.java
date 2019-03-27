package fr.cgaiton611.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.dao.CompanyDAO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.Company;

@Service
public class CompanyService {
	@Autowired
	CompanyDAO companyDAO;

	public List<Company> findPage(int page, int elements) throws DAOException {
		return companyDAO.findPage(page, elements);
	}

	public Company find(Company company) throws DAOException {
		return companyDAO.find(company);
	}

	public Company findByName(String name) throws DAOException {
		return companyDAO.findByName(name);
	}

	public Company create(String name) throws DAOException {
		return companyDAO.create(new Company(name));
	}

	public int count() throws DAOException {
		return companyDAO.count();
	}

	public List<String> findAllName() throws DAOException {
		return companyDAO.findAllName();
	}

	public void delete(long id) throws DAOException {
		companyDAO.delete(new Company(id));
	}
}
