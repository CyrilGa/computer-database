package fr.cgaiton611.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.model.ComputerValidator;
import fr.cgaiton611.persistence.ComputerDAO;

public class ComputerService {
	ComputerDAO computerDAO = ComputerDAO.getInstance();

	private static ComputerService instance = new ComputerService();

	private ComputerService() {
	};

	public static ComputerService getInstance() {
		return instance;
	}

	public List<Computer> findPaged(int page, int elements) {
		return computerDAO.findPaged(page, elements);
	}

	public Optional<Computer> find(long id) {
		return computerDAO.find(new Computer(id));
	}

	public Optional<Computer> create(String name, Date introduced, Date discontinued, long companyId) {
		Computer computer = new Computer(name, introduced, discontinued, new Company(companyId));
		if (!ComputerValidator.validate(computer)) {
			return Optional.empty();
		}
		return computerDAO.create(computer);
	}

	public Optional<Computer> update(long id, Optional<String> name, Optional<Date> introduced,
			Optional<Date> discontinued, Optional<Long> companyId) {
		
		Optional<Computer> old = find(id);
		if (! old.isPresent()) return Optional.empty();
		Computer computer = old.get();
		if (! name.isPresent()) computer.setName(name.get());
		if (! introduced.isPresent()) computer.setIntroduced(introduced.get());
		if (! discontinued.isPresent()) computer.setDiscontinued(discontinued.get());
		if (! companyId.isPresent()) computer.setCompany(new Company(companyId.get()));
		
		if (!ComputerValidator.validate(computer)) {
			return Optional.empty();
		}
		return computerDAO.update(computer);
	}

	public void delete(long id) {
		computerDAO.delete(new Computer(id));
	}

	public int count() {
		return computerDAO.count();
	}
}
