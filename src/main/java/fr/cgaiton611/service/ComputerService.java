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
	CompanyService companyService = CompanyService.getInstance();

	private static ComputerService instance = new ComputerService();

	private ComputerService() {
	};

	public static ComputerService getInstance() {
		return instance;
	}

	public List<Computer> findPaged(int page, int elements) {
		List<Computer> computers = computerDAO.findPaged(page, elements);
		for (Computer computer : computers) {
			Optional<Company> company = companyService.find(computer.getId());
			if (company.isPresent())
				computer.setCompany(company.get());
		}
		return computers;
	}

	public Optional<Computer> find(long id) {
		Optional<Computer> computer = computerDAO.find(new Computer(id));
		if (!computer.isPresent())
			return Optional.empty();
		Optional<Company> company = companyService.find(computer.get().getId());
		if (company.isPresent())
			computer.get().setCompany(company.get());
		return computer;
	}

	public Optional<Computer> create(String name, Date introduced, Date discontinued, long companyId) {
		Optional<Company> company = companyService.find(companyId);
		if (!company.isPresent())
			return Optional.empty();
		Computer computer = new Computer(name, introduced, discontinued, company.get());
		if (!ComputerValidator.validate(computer)) {
			return Optional.empty();
		}
		return computerDAO.create(computer);
	}

	public Optional<Computer> update(long id, Optional<String> name, Optional<Date> introduced,
			Optional<Date> discontinued, Optional<Long> companyId) {

		Optional<Computer> old = find(id);
		if (!old.isPresent())
			return Optional.empty();
		Computer computer = old.get();
		if (name.isPresent())
			computer.setName(name.get());
		if (introduced.isPresent())
			computer.setIntroduced(introduced.get());
		if (discontinued.isPresent())
			computer.setDiscontinued(discontinued.get());
		if (companyId.isPresent())
			computer.setCompany(new Company(companyId.get()));

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
	
	public List<Computer> findByNamePaged(int page, int elements, String name) {
		List<Computer> computers = computerDAO.findByNamePaged(page, elements, name);
		for (Computer computer : computers) {
			Optional<Company> company = companyService.find(computer.getId());
			if (company.isPresent())
				computer.setCompany(company.get());
		}
		return computers;
	}
	
	public int countByName(String name) {
		return computerDAO.countByName(name);
	}
		
}
