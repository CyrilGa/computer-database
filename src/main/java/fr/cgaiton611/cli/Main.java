package fr.cgaiton611.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.cgaiton611.cli.util.PrintUtil;
import fr.cgaiton611.cli.util.ScanUtil;

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

		ScanUtil scanUtil = new ScanUtil();
		PrintUtil printUtil = new PrintUtil();
		Facade facade = new Facade();
		final Logger logger = LoggerFactory.getLogger(Main.class);
		String input;

		while (true) {

			printUtil.print(">>> ");
			input = scanUtil.getLine();

			if ("ls computer".equals(input)) {
				logger.info("ls computer");
				facade.findPagedComputer();
			} else if ("ls company".equals(input)) {
				logger.info("ls computer");
				facade.findPagedCompany();
			} else if ("find computer".equals(input)) {
				logger.info("find computer");
				facade.findComputerById();
			} else if ("create computer".equals(input)) {
				logger.info("create computer");
				facade.createComputer();
			} else if ("update computer".equals(input)) {
				logger.info("update computer");
				facade.updateComputer();
			} else if ("delete computer".equals(input)) {
				logger.info("delete computer");
				facade.deleteComputer();
			} else if ("help".equals(input)) {
				logger.info("helper");
				facade.helper();
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
