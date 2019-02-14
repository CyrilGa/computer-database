package fr.cgaiton611.service;

import java.sql.Timestamp;
import java.util.List;

import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;


public class ComputerService {
	ComputerDAO computerDAO = new ComputerDAO();
	
	public List<Computer> findPaged(int page){
		int elements = 15;
		return computerDAO.findPaged(page, elements);
	}
	
	public Computer find(int id) {
		return computerDAO.find(new Computer(id));
	}
	
	public long create(String name, Timestamp introduced, Timestamp discontinued, long companyId) {
		return computerDAO.create(new Computer(name, introduced, discontinued, companyId)).getId();
	}
	
	public long update(int id, String name, Timestamp introduced, Timestamp discontinued, long companyId) {
		return computerDAO.update(new Computer(name, introduced, discontinued, companyId)).getId();
	}
	
	public void delete(int id) {
		computerDAO.delete(new Computer(id));
	}
}
