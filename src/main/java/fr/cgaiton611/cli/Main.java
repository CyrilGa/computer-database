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
		UILauncherFacade uiLauncherFacade = new UILauncherFacade();
		final Logger logger = LoggerFactory.getLogger(Main.class);
		String input;

		while (true) {

			printUtil.print(">>> ");
			input = scanUtil.getLine();

			if ("ls computer".equals(input)) {
				logger.info("ls computer");
				uiLauncherFacade.showPagedComputer();
			} else if ("ls company".equals(input)) {
				logger.info("ls computer");
				uiLauncherFacade.showPagedCompany();
			} else if ("find computer".equals(input)) {
				logger.info("find computer");
				uiLauncherFacade.findComputerById();
			} else if ("create computer".equals(input)) {
				logger.info("create computer");
				uiLauncherFacade.createComputer();
			} else if ("update computer".equals(input)) {
				logger.info("update computer");
				uiLauncherFacade.updateComputer();
			} else if ("delete computer".equals(input)) {
				logger.info("delete computer");
				uiLauncherFacade.deleteComputer();
			} else if ("help".equals(input)) {
				logger.info("helper");
				uiLauncherFacade.helper();
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
