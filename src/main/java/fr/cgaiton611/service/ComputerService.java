package fr.cgaiton611.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.EmptyResultSetException;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;

@Service
public class ComputerService {
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyService companyService;

//	public List<Computer> findPaged(int page, int elements) throws DAOException {
//		List<Computer> computers = computerDAO.findPaged(page, elements);
//		for (Computer computer : computers) {
//			Optional<Company> company = companyService.find(computer.getId());
//			if (company.isPresent())
//				computer.setCompany(company.get());
//		}
//		return computers;
//	}

	public Computer find(long id) throws DAOException {
		Computer computer = computerDAO.find(new Computer(id));
		try {
			Company company = companyService.find(computer.getCompany().getId());
			computer.setCompany(company);
		} catch (EmptyResultSetException e) {
			computer.setCompany(null);
		}
		return computer;
	}

//	public Computer create(String name, Date introduced, Date discontinued, long companyId)
//			throws DAOException {
//		Company company = companyService.find(companyId);
//		Computer computer = new Computer(name, introduced, discontinued, company.orElse(null));
//		return computerDAO.create(computer);
//	}

	public Computer create(Computer computer) throws DAOException {
		return computerDAO.create(computer);
	}

	public Computer update(Computer computer) throws DAOException {
		return computerDAO.update(computer);
	}

	public void delete(long id) throws DAOException {
		computerDAO.delete(new Computer(id));
	}

	public int count() throws DAOException {
		return computerDAO.count();
	}

	public List<Computer> findPageWithParameters(int page, int elements, String computerName, String companyName, String orderByName, String orderByOrder)
			throws DAOException {
		return computerDAO.findPageWithParameters(page, elements, computerName, companyName, orderByName, orderByOrder);
	}

	public int countWithParameters(String computerName, String companyName) throws DAOException {
		return computerDAO.countWithParameters(computerName, companyName);
	}

}
