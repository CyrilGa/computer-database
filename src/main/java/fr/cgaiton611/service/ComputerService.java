package fr.cgaiton611.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;
import fr.cgaiton611.validation.ComputerValidator;

@Service
public class ComputerService {
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyService companyService;

	
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
		Optional<Company> company = companyService.find(computer.get().getCompany().getId());
		if (company.isPresent())
			computer.get().setCompany(company.get());
		return computer;
	}

	public Optional<Computer> create(String name, Date introduced, Date discontinued, long companyId) {
		Optional<Company> company = companyService.find(companyId);
		Computer computer = new Computer(name, introduced, discontinued, company.orElse(null));
		if (!ComputerValidator.validate(computer)) {
			return Optional.empty();
		}
		return computerDAO.create(computer);
	}

	public Optional<Computer> create(Computer computer) {
		return create(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),
				computer.getCompany().getId());
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

	public Optional<Computer> update(Computer computer) {
		return update(computer.getId(), Optional.ofNullable(computer.getName()),
				Optional.ofNullable(computer.getIntroduced()), Optional.ofNullable(computer.getDiscontinued()),
				Optional.ofNullable(computer.getCompany().getId()));
	}

	public void delete(long id) {
		computerDAO.delete(new Computer(id));
	}

	public int count() {
		return computerDAO.count();
	}

	public List<Computer> findByNamePaged(int page, int elements, String computerName, String companyName) {
		return computerDAO.findByNamePaged(page, elements, computerName, companyName);
	}

	public int countByName(String computerName, String companyName) {
		return computerDAO.countByName(computerName, companyName);
	}

}
