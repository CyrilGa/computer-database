package fr.cgaiton611.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.cgaiton611.springconfig.SpringConfig;

/**
 * Main class for the cli Used like a stateless automaton
 * 
 * @author cyril
 * @version 1.0
 */
public class Main {

	/**
	 * Wait for the user entry and call facade method appropriately
	 * 
	 * @param args Arguments
	 */
	
	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		CLIMenuFacade cliMenuFacade = context.getBean(CLIMenuFacade.class);
		
		ScanUtil scanUtil = new ScanUtil();
		PrintUtil printUtil = new PrintUtil();
		final Logger logger = LoggerFactory.getLogger(Main.class);
		String input;

		while (true) {

			printUtil.print(">>> ");
			input = scanUtil.getLine();

			if ("ls computer".equals(input)) {
				logger.info("ls computer");
				cliMenuFacade.showPagedComputer();
			} else if ("ls company".equals(input)) {
				logger.info("ls computer");
				cliMenuFacade.showPagedCompany();
			} else if ("find computer".equals(input)) {
				logger.info("find computer");
				cliMenuFacade.findComputerById();
			} else if ("create computer".equals(input)) {
				logger.info("create computer");
				cliMenuFacade.createComputer();
			} else if ("update computer".equals(input)) {
				logger.info("update computer");
				cliMenuFacade.updateComputer();
			} else if ("delete computer".equals(input)) {
				logger.info("delete computer");
				cliMenuFacade.deleteComputer();
			} else if ("delete company".equals(input)) {
				logger.info("delete company");
				cliMenuFacade.deleteCompany();
			} else if ("help".equals(input)) {
				logger.info("helper");
				cliMenuFacade.helper();
			} else if ("exit".equals(input)) {
				logger.info("exit");
				printUtil.printn("Exit!");
				break;
			} else {
				logger.info("invalid command");
				printUtil.printn("Type 'help' for help");
			}

		}

		scanUtil.closeScanner();
	}

}
