package fr.cgaiton611.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.page.CompanyPage;
import fr.cgaiton611.page.ComputerPage;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;

/**
 * Class serving as a front-facing interface for treating the cli
 * 
 * @author cyril
 * @version 1.0
 */

@Controller
public class CLIMenuFacade {

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
		computerPage.init();
		List<Computer> computers = computerPage.next();
		while (true) {
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
				computers = computerPage.previous();
			} else if (input.equals("n")) {
				System.out.println("n");
				computers = computerPage.next();
			} else if (input.equals("e"))
				break;
		}
	}

	/**
	 * Use pagination to return a list of companies
	 */
	public void showPagedCompany() {
		companyPage.init();
		List<Company> companies = companyPage.next();
		while (true) {
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
				companies = companyPage.previous();
			else if (input.equals("n"))
				companies = companyPage.next();
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

		Optional<Computer> computer = computerService.find(id.get());
		if (!computer.isPresent())
			printUtil.printn("Computer not found");
		else
			printUtil.printn(computer);
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

		Optional<Computer> computer = computerService.create(name.get(), introduced.orElse(null),
				discontinued.orElse(null), companyId.get());
		if (!computer.isPresent())
			printUtil.printn("Computer not created");
		else
			printUtil.printn("Computer sucefully created ! (id: " + computer.get().getId() + ")");
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

		Optional<Computer> computer = computerService.update(id.get().longValue(), name, introduced, discontinued,
				companyId);
		if (!computer.isPresent())
			printUtil.printn("Computer not updated");
		else
			printUtil.printn("Computer sucefully updated !");
	}

	/**
	 * Delete the computer with the id given
	 */
	public void deleteComputer() {
		Optional<Long> id = scanUtil.askLong("id", false);
		if (!id.isPresent())
			return;
		computerService.delete(id.get());
		printUtil.printn("Computer sucefully deleted !");
	}

	public void deleteCompany() {
		Optional<Long> id = scanUtil.askLong("id", false);
		if (!id.isPresent())
			return;
		companyService.delete(id.get());
		printUtil.printn("Company and computers sucefully deleted !");
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
