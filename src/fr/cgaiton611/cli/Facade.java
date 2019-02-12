package fr.cgaiton611.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.CompanyDAO;
import fr.cgaiton611.persistence.ComputerDAO;

public class Facade {
	

	Scanner scanner = new Scanner(System.in);
	CompanyDAO companyDAO = new CompanyDAO();
	ComputerDAO computerDAO = new ComputerDAO();
	
	
	public void findAllComputer() {
		int nb_elements = 15;
		int page = 0;
		while (true) {
			List<Computer> computers = computerDAO.find_all(page*nb_elements, (page+1)*nb_elements);
	    	for (Computer computer : computers) {
				System.out.println(computer.toString());
			}
	    	System.out.println("p for previous, n for next, q for quit");
	        String command = scanner.nextLine();
	        while ((!command.equals("p")) && (!command.equals("n")) && (!command.equals("q"))) {
		    	System.out.println("p for previous, n for next, q for quit");
		        command = scanner.nextLine();
	        }
	        if (command.equals("p")) {
	        	page--;
	        	if (page < 0) page = 0;
	        }
	        else if (command.equals("n")){
	        	page++;
	        }
	        else if (command.equals("q")){
	        	break;
	        }
		}
	}
	
	public void findAllCompany() {
    	int nb_elements = 15;
		int page = 0;
		while (true) {
			List<Company> companies = companyDAO.find_all(page*nb_elements, (page+1)*nb_elements);
	    	for (Company company : companies) {
				System.out.println(company.toString());
			}
	    	System.out.println("p for previous, n for next, q for quit");
	        String command = scanner.nextLine();
	        while ((!command.equals("p")) && (!command.equals("n")) && (!command.equals("q"))) {
		    	System.out.println("p for previous, n for next, q for quit");
		        command = scanner.nextLine();
	        }
	        if (command.equals("p")) {
	        	page--;
	        	if (page < 0) page = 0;
	        }
	        else if (command.equals("n")){
	        	page++;
	        }
	        else if (command.equals("q")){
	        	break;
	        }
		}
	}
	
	public void findComputerById() {
		System.out.println("Id: ");
        String id_str = scanner.nextLine();
		int id = 0;
		try {
			id = Integer.parseInt(id_str);
		}
		catch (Exception e) {
			System.out.println("<id:integer>");
		}
		Computer computer = computerDAO.find(new Computer(id));
		System.out.println(computer.toString());
	}
	
	public void createComputer() {
		// name
		System.out.println("Name: ");
        String name = scanner.nextLine();
		
		// company_id
		System.out.println("Company id: ");
		String company_id_str = scanner.nextLine();
		int company_id = 0;
		try {
			company_id = Integer.parseInt(company_id_str);
		}
		catch (Exception e) {
			System.out.println("<id:integer>");
			return;
		}
		
		// introduced
		Timestamp introduced = new Timestamp(new Date().getTime());
		
		Computer computer = new Computer(name, introduced, null, company_id);
		computerDAO.create(computer);
		System.out.println("Computer sucefully created !");
	}
	
	public void updateComputer() {
		// id
		System.out.println("id to update: ");
		int id = Integer.parseInt(scanner.nextLine());
		
		// name
		System.out.println("New name: ");
        String name = scanner.nextLine();
		
		// company_id
		System.out.println("New company id: ");
		int company_id = Integer.parseInt(scanner.nextLine());
		
		Computer computer = new Computer(id, name, null, null, company_id);
		computerDAO.update(computer);
		System.out.println("Computer sucefully updated !");
	}
	
	public void deleteComputer() {
		System.out.println("Id: ");
        String id_str = scanner.nextLine();
		int id = 0;
		try {
			id = Integer.parseInt(id_str);
		}
		catch (Exception e) {
			System.out.println("<id:integer>");
		}
		computerDAO.delete(new Computer(id));
		System.out.println("Computer sucefully deleted !");
	}
	
	public void helper() {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get("helper.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println(content);
	}
}
