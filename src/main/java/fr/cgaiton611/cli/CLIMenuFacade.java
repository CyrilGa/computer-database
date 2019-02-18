package fr.cgaiton611.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.cgaiton611.cli.util.PrintUtil;
import fr.cgaiton611.cli.util.ScanUtil;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.CompanyPage;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.model.ComputerPage;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;

/**
 * Class serving as a front-facing interface for treating the cli
 * 
 * @author cyril
 * @version 1.0
 */
public class CLIMenuFacade {

	ScanUtil scanUtil = new ScanUtil();
	PrintUtil printUtil = new PrintUtil();
	ComputerService computerService = ComputerService.getInstance();
	CompanyService companyService = CompanyService.getInstance();

	/**
	 * Use pagination to return a list of computers
	 */
	public void showPagedComputer() {
		ComputerPage computerPage = new ComputerPage();
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
			if (input.equals("p"))
				computers = computerPage.previous();
			else if (input.equals("n"))
				computers = computerPage.next();
			else if (input.equals("e"))
				break;
		}
	}

	/**
	 * Use pagination to return a list of companies
	 */
	public void showPagedCompany() {
		CompanyPage companyPage = new CompanyPage();
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
		Optional<Integer> id = scanUtil.askInteger("id", false);
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

		Optional<Integer> companyId = scanUtil.askInteger("company_id", false);
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
		Optional<Integer> id = scanUtil.askInteger("id", false);
		if (!id.isPresent())
			return;

		Optional<String> name = scanUtil.askString("name", true);

		Optional<Date> introduced = scanUtil.askDate("introduced", true);

		Optional<Date> discontinued = scanUtil.askDate("discontinued", true);

		Optional<Integer> companyId = scanUtil.askInteger("company_id", true);

		Optional<Computer> computer = computerService.update(id.get(), name, introduced,
				discontinued, companyId);
		if (!computer.isPresent())
			printUtil.printn("Computer not updated");
		else
			printUtil.printn("Computer sucefully updated !");
	}

	/**
	 * Delete the computer with the id given
	 */
	public void deleteComputer() {
		Optional<Integer> id = scanUtil.askInteger("id", false);
		if (!id.isPresent())
			return;
		computerService.delete(id.get());
		printUtil.printn("Computer sucefully deleted !");
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
