package fr.cgaiton611.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.CompanyDAO;
import fr.cgaiton611.persistence.ComputerDAO;

/**
 * Class serving as a front-facing interface for treating the cli
 * @author cyril
 * @version 1.0
 */
public class Facade {


	Scanner scanner = new Scanner(System.in);
	CompanyDAO companyDAO = new CompanyDAO();
	ComputerDAO computerDAO = new ComputerDAO();
	Validator validator = new Validator();
	ScanUtil scanUtil = new ScanUtil();

	/**
	 * Use pagination to return a list of computers
	 */
	public void findAllComputer() {
		int nb_elements = 15;
		int page = 0;
		while (true) {
			List<Computer> computers = computerDAO.find_all(page, nb_elements);
			for (Computer computer : computers) {
				System.out.println(computer.toString());
			}
			System.out.println("p for previous, n for next, e for exit");
			System.out.printf("--> ");
			String command = scanner.nextLine();
			while ((!command.equals("p")) && (!command.equals("n")) && (!command.equals("e"))) {
				System.out.println("p for previous, n for next, e for exit");
				System.out.printf("--> ");
				command = scanner.nextLine();
			}
			if (command.equals("p")) {
				page--;
				if (page < 0) page = 0;
			}
			else if (command.equals("n")){
				page++;
			}
			else if (command.equals("e")){
				break;
			}
		}
	}

	/**
	 * Use pagination to return a list of companies
	 */
	public void findAllCompany() {
		int nb_elements = 15;
		int page = 0;
		while (true) {
			List<Company> companies = companyDAO.find_all(page, nb_elements);
			for (Company company : companies) {
				System.out.println(company.toString());
			}
			System.out.println("p for previous, n for next, e for exit");
			System.out.printf("--> ");
			String command = scanner.nextLine();
			while ((!command.equals("p")) && (!command.equals("n")) && (!command.equals("e"))) {
				System.out.println("p for previous, n for next, e for exit");
				System.out.printf("--> ");
				command = scanner.nextLine();
			}
			if (command.equals("p")) {
				page--;
				if (page < 0) page = 0;
			}
			else if (command.equals("n")){
				page++;
			}
			else if (command.equals("e")){
				break;
			}
		}
	}

	/**
	 * Get the computer with the id given
	 */
	public void findComputerById() {
		Integer id = scanUtil.askInteger("id", false);
		if (id == null) return;
		Computer computer = computerDAO.find(new Computer(id));
		if (computer == null) System.out.println("Computer not found");
		else System.out.println(computer.toString());
	}

	/**
	 * Create a computer with all information given
	 */
	public void createComputer() {
		// name
		String name = scanUtil.askString("name", false);
		if (name == null) return;
		
		// introduced
		Timestamp introduced = scanUtil.askTimestamp("introduced", true);

		// discontinued
		Timestamp discontinued = scanUtil.askTimestamp("discontinued", true);

		// company_id
		Integer company_id = scanUtil.askInteger("company_id", false);
		if (company_id == null) return;

		Computer computer = new Computer(name, introduced, discontinued, company_id);
		computer = computerDAO.create(computer);
		System.out.println("Computer sucefully created ! (id: "+computer.getId()+")");
	}

	/**
	 * Update the computer with the id given and replace his fields with the information given
	 */
	public void updateComputer() {
		// id
		Integer id = scanUtil.askInteger("id", false);
		if (id == null) return;

		// name
		String name = scanUtil.askString("name", true);

		// introduced
		Timestamp introduced = scanUtil.askTimestamp("introduced", true);

		// discontinued
		Timestamp discontinued = scanUtil.askTimestamp("introduced", true);
		

		// company_id
		Integer company_id = scanUtil.askInteger("company_id", true);
		if (company_id == null) return;

		Computer computer = new Computer(id, name, introduced, discontinued, company_id);
		computerDAO.update(computer);
		System.out.println("Computer sucefully updated !");
	}

	/**
	 * Delete the computer with the id given
	 */
	public void deleteComputer() {
		Integer id = scanUtil.askInteger("id", false);
		if (id == null) return;
		computerDAO.delete(new Computer(id));
		System.out.println("Computer sucefully deleted !");
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
