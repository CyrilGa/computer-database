package fr.cgaiton611.cli;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the cli
 * Used like a stateless automaton
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
		final Logger logger = LoggerFactory.getLogger(Main.class);
		
		while (true) {

            System.out.printf(">>> ");
            String input = scanner.nextLine();

            if ("ls computer".equals(input)) {
            	logger.info("ls computer");
            	facade.findAllComputer();
            }
            else if ("ls company".equals(input)) {
            	logger.info("ls computer");
            	facade.findAllCompany();
            }
			else if ("find computer".equals(input)) {
            	logger.info("find computer");
				facade.findComputerById();
			}
			else if ("create computer".equals(input)) {
            	logger.info("create computer");
				facade.createComputer();
			}
			else if ("update computer".equals(input)) {
            	logger.info("update computer");
				facade.updateComputer();
			}
			else if ("delete computer".equals(input)) {
            	logger.info("delete computer");
				facade.deleteComputer();
			}
            else if ("help".equals(input)) {
            	logger.info("helper");
            	facade.helper();
            }
            else if ("exit".equals(input)) {
            	logger.info("exit");
                System.out.println("Exit!");
                break;
            }
            else {
            	logger.info("invalid command");
            	System.out.println("Type 'help' for help");
            }

		}
		
		scanner.close();
	}
	
}
