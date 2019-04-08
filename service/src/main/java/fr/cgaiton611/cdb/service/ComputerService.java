package fr.cgaiton611.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.dao.ComputerDAO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.Computer;

@Service
public class ComputerService {
	@Autowired
	private ComputerDAO computerDAO;

	public Computer find(Computer computer) throws DAOException {
		return computerDAO.find(computer);
	}

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

	public List<Computer> findPageWithParameters(int page, int elements, String computerName, String companyName,
			String orderByName, String orderByOrder) throws DAOException {
		return computerDAO.findPage(page, elements, computerName, companyName, orderByName, orderByOrder);
	}

	public int countWithParameters(String computerName, String companyName) throws DAOException {
		return computerDAO.count(computerName, companyName);
	}

}
