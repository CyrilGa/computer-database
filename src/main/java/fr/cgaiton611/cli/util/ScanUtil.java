package fr.cgaiton611.cli.util;

import java.sql.Timestamp;
import java.util.Scanner;

import fr.cgaiton611.cli.Validator;

/**
 * Ask, verify, cast and return the user input.
 * @author cyril
 * @version 1.0
 */
public class ScanUtil {
	
	Scanner scanner = new Scanner(System.in);
	Validator validator = new Validator();
	PrintUtil printUtil = new PrintUtil();
	ConvertUtil convertUtil = new ConvertUtil();

	/**
	 * Ask the user the give a certain input (here: an Integer)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @param t Generic variable used to test the type of T
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public <T> String askSomething(String msg, boolean skip, T t) {
		String skipMsg = "";
		if (skip) skipMsg = "(enter to skip)";
		printUtil.print(msg+" "+skipMsg+" : ");
		String input = getLine();
		if ("".equals(input) && skip) return null;
		int cnt = 0;
		int max_cnt = 3;
		while (cnt++ < max_cnt) {
			if ("e".equals(input)) return null;
			else if (validator.isType(input, t)) {
				return input;
			}
			if (cnt == max_cnt) break;
			System.out.println(msg+" must be an "+ t.getClass().getCanonicalName() +" (e for exit)");
			System.out.printf(msg+" : ");
			input = getLine();
		}
		System.out.println("exit...");
		return null;
	}

	/**
	 * Ask the user the give a certain input (here: an Integer)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Integer askInteger(String msg, boolean skip) {
		return convertUtil.stringToInt(askSomething(msg, skip, new Integer(0)));
	}
	
	/**
	 * Ask the user the give a certain input (here: an Timestamp)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Timestamp askTimestamp(String msg, boolean skip) {
		return convertUtil.stringToTimastamp(askSomething(msg, skip, new Timestamp(0)));
	}
	
	/**
	 * Ask the user the give a certain input (here: an String)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public String askString(String msg, boolean skip) {
		return askSomething(msg, skip, new String(""));
	}
	
	public String getLine() {
		return scanner.nextLine();
	}
	
	public void closeScanner() {
		scanner.close();
	}

}
