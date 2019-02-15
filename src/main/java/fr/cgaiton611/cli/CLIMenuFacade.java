package fr.cgaiton611.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import fr.cgaiton611.cli.util.PrintUtil;
import fr.cgaiton611.cli.util.ScanUtil;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
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
	ComputerService computerService = new ComputerService();
	CompanyService companyService = new CompanyService();

	/**
	 * Use pagination to return a list of computers
	 */
	public void showPagedComputer() {
		int page = 0;
		while (true) {
			List<Computer> computers = computerService.findPaged(page);
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
				page = page == 0 ? 0 : page--;
			else if (input.equals("n"))
				page++;
			else if (input.equals("e"))
				break;
		}
	}

	/**
	 * Use pagination to return a list of companies
	 */
	public void showPagedCompany() {
		int page = 0;
		while (true) {
			List<Company> company = companyService.findPaged(page);
			printUtil.printEntities(company);
			printUtil.printn("p for previous, n for next, e for exit");
			printUtil.print("--> ");
			String input = scanUtil.getLine();
			while ((!input.equals("p")) && (!input.equals("n")) && (!input.equals("e"))) {
				printUtil.printn("p for previous, n for next, e for exit");
				printUtil.print("--> ");
				input = scanUtil.getLine();
			}
			if (input.equals("p"))
				page = page == 0 ? 0 : page--;
			else if (input.equals("n"))
				page++;
			else if (input.equals("e"))
				break;
		}
	}

	/**
	 * Get the computer with the id given
	 */
	public void findComputerById() {
		Integer id = scanUtil.askInteger("id", false);
		if (id == null)
			return;

		Computer computer = computerService.find(id);
		if (computer == null)
			printUtil.printn("Computer not found");
		else
			printUtil.printn(computer);
	}

	/**
	 * Create a computer with all information given
	 */
	public void createComputer() {
		String name = scanUtil.askString("name", false);
		if (name == null)
			return;

		Timestamp introduced = scanUtil.askTimestamp("introduced", true);

		Timestamp discontinued = scanUtil.askTimestamp("discontinued", true);

		Integer companyId = scanUtil.askInteger("company_id", false);
		if (companyId == null)
			return;

		long id = computerService.create(name, introduced, discontinued, companyId);
		printUtil.printn("Computer sucefully created ! (id: " + id + ")");
	}

	/**
	 * Update the computer with the id given and replace his fields with the
	 * information given
	 */
	public void updateComputer() {
		// id
		Integer id = scanUtil.askInteger("id", false);
		if (id == null)
			return;

		String name = scanUtil.askString("name", true);
		if (name == null)
			return;

		Timestamp introduced = scanUtil.askTimestamp("introduced", true);

		Timestamp discontinued = scanUtil.askTimestamp("discontinued", true);

		Integer companyId = scanUtil.askInteger("company_id", true);

		computerService.update(id, name, introduced, discontinued, companyId);
		printUtil.printn("Computer sucefully updated !");
	}

	/**
	 * Delete the computer with the id given
	 */
	public void deleteComputer() {
		Integer id = scanUtil.askInteger("id", false);
		if (id == null)
			return;
		computerService.delete(id);
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
