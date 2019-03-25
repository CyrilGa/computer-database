package fr.cgaiton611.cdb.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.NoResultRowException;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.model.Computer;
import fr.cgaiton611.cdb.page.CompanyPage;
import fr.cgaiton611.cdb.page.ComputerPage;
import fr.cgaiton611.cdb.service.CompanyService;
import fr.cgaiton611.cdb.service.ComputerService;

/**
 * Class serving as a front-facing interface for treating the cli
 * 
 * @author cyril
 * @version 1.0
 */

@Controller
public class CLIMenuFacade {
	private final Logger logger = LoggerFactory.getLogger(CLIMenuFacade.class);

	ScanUtil scanUtil = new ScanUtil();
	PrintUtil printUtil = new PrintUtil();

	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerPage computerPage;
	@Autowired
	CompanyPage companyPage;

	/**
	 * Use pagination to return a list of computers
	 */
	public void showPagedComputer() {
		List<Computer> computers;
		while (true) {
			computers = new ArrayList<>();
			try {
				computers = computerPage.getCurrent();
			} catch (DAOException e) {
				logger.warn(e.getMessage());
			}
			printUtil.printEntities(computers);
			printUtil.printn("p for previous, n for next, e for exit");
			printUtil.print("--> ");
			String input = scanUtil.getLine();
			while ((!input.equals("p")) && (!input.equals("n")) && (!input.equals("e"))) {
				printUtil.printn("p for previous, n for next, e for exit");
				printUtil.print("--> ");
				input = scanUtil.getLine();
			}
			if (input.equals("p")) {
				System.out.println("p");
				computerPage.pageInc();
			} else if (input.equals("n")) {
				System.out.println("n");
				computerPage.pageDec();
			} else if (input.equals("e"))
				break;
		}
	}

	/**
	 * Use pagination to return a list of companies
	 */
	public void showPagedCompany() {
		List<Company> companies;
		while (true) {
			companies = new ArrayList<>();
			try {
				companies = companyPage.getCurrent();
			} catch (DAOException e) {
				logger.warn(e.getMessage());
			}
			printUtil.printEntities(companies);
			printUtil.printn("p for previous, n for next, e for exit");
			printUtil.print("--> ");
			String input = scanUtil.getLine();
			while ((!input.equals("p")) && (!input.equals("n")) && (!input.equals("e"))) {
				printUtil.printn("p for previous, n for next, e for exit");
				printUtil.print("--> ");
				input = scanUtil.getLine();
			}
			if (input.equals("p"))
				companyPage.pageInc();
			else if (input.equals("n"))
				companyPage.pageDec();
			else if (input.equals("e"))
				break;
		}
	}

	/**
	 * Get the computer with the id given
	 */
	public void findComputerById() {
		Optional<Long> id = scanUtil.askLong("id", false);
		if (!id.isPresent())
			return;

		Computer computer;
		try {
			computer = computerService.find(id.get());
			printUtil.printn(computer);
		} catch (NoResultRowException e){
			logger.warn(e.getMessage());
			printUtil.printn("Computer not found");
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			printUtil.printn("Database error");
		}
	}

	/**
	 * Create a computer with all information given
	 */
	public void createComputer() {
		Optional<String> name = scanUtil.askString("name", false);
		if (!name.isPresent())
			return;

		Optional<Date> introduced = scanUtil.askDate("introduced", true);

		Optional<Date> discontinued = scanUtil.askDate("discontinued", true);

		Optional<Long> companyId = scanUtil.askLong("company_id", false);
		if (!companyId.isPresent())
			return;

		Company company;
		try {
			company = companyService.find(new Company(companyId.get()));
		} catch (NoResultRowException e) {
			company = null;
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			printUtil.printn("Computer not created, database error");
			return;
		}
		Computer computer;
		try {
			computer = computerService
					.create(new Computer(name.get(), introduced.orElse(null), discontinued.orElse(null), company));
			printUtil.printn("Computer sucefully created ! (id: " + computer.getId() + ")");
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			printUtil.printn("Computer not created, database error");
		}
	}

	/**
	 * Update the computer with the id given and replace his fields with the
	 * information given
	 */
	public void updateComputer() {
		// id
		Optional<Long> id = scanUtil.askLong("id", false);
		if (!id.isPresent())
			return;

		Optional<String> name = scanUtil.askString("name", true);

		Optional<Date> introduced = scanUtil.askDate("introduced", true);

		Optional<Date> discontinued = scanUtil.askDate("discontinued", true);

		Optional<Long> companyId = scanUtil.askLong("company_id", true);

		Company company = null;
		if (companyId.isPresent()) {
			try {
				company = companyService.find(new Company(companyId.get()));
			} catch (NoResultRowException e) {
				company = null;
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				printUtil.printn("Computer not updated, database error");
				return;
			}
		}

		try {
			computerService.update(new Computer(id.get(), name.orElse(null), introduced.orElse(null),
					discontinued.orElse(null), company));
			logger.debug(new Computer(id.get(), name.orElse(null), introduced.orElse(null),
					discontinued.orElse(null), company).toString());
			printUtil.printn("Computer sucefully updated !");
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			printUtil.printn("Computer not updated, database error");
		}
	}

	/**
	 * Delete the computer with the id given
	 */
	public void deleteComputer() {
		Optional<Long> id = scanUtil.askLong("id", false);
		if (!id.isPresent())
			return;
		String msg = "Computer sucefully deleted !";
		try {
			computerService.delete(id.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			msg = "Computer not deleted";
		}
		printUtil.printn(msg);
	}

	public void deleteCompany() {
		Optional<Long> id = scanUtil.askLong("id", false);
		if (!id.isPresent())
			return;
		String msg = "Company and computers sucefully deleted !";
		try {
			companyService.delete(id.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			msg = "Company and computers not deleted";
		}
		printUtil.printn(msg);
	}

	/**
	 * Display the file containing the helping text
	 */
	public void helper() {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get("ressources/helper.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(content);
	}
}
