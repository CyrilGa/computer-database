package fr.cgaiton611.cli;

import java.util.Scanner;

/**
 * Main class for the cli
 * @author cyril
 * @version 1.0
 */
public class Main {
	
	/**
	 * Wait for the user entry and call facade method appropriately
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		Facade facade = new Facade();
		
		while (true) {

            System.out.print(">>> ");
            String input = scanner.nextLine();

            if ("ls computer".equals(input)) {
            	facade.findAllComputer();
            }
            else if ("ls company".equals(input)) {
            	facade.findAllCompany();
            }
			else if ("get computer".equals(input)) {
				facade.findComputerById();
			}
			else if ("create computer".equals(input)) {
				facade.createComputer();
			}
			else if ("update computer".equals(input)) {
				facade.updateComputer();
			}
			else if ("delete computer".equals(input)) {
				facade.deleteComputer();
			}
            else if ("help".equals(input)) {
            	facade.helper();
            }
            else if ("exit".equals(input)) {
                System.out.println("Exit!");
                break;
            }
            else {
            	System.out.println("Type 'help' for help");
            }

		}
		
		scanner.close();
	}
	
}
