package fr.cgaiton611.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;

public class ComputerService {
	ComputerDAO computerDAO = ComputerDAO.getInstance();
	
	private static ComputerService instance = new ComputerService();
	
	private ComputerService() {};
	
	public static ComputerService getInstance() {
		return instance;
	}

	public List<Computer> findPaged(int page, int elements) {
		return computerDAO.findPaged(page, elements);
	}

	public Optional<Computer> find(int id) {
		return computerDAO.find(new Computer(id));
	}

	public Optional<Computer> create(String name, Timestamp introduced, Timestamp discontinued, long companyId) {
		return computerDAO.create(new Computer(name, introduced, discontinued, companyId));
	}

	public Optional<Computer> update(int id, String name, Timestamp introduced, Timestamp discontinued, long companyId) {
		return computerDAO.update(new Computer(name, introduced, discontinued, companyId));
	}

	public void delete(int id) {
		computerDAO.delete(new Computer(id));
	}
	
	public int count() {
		return computerDAO.count();
	}
}
