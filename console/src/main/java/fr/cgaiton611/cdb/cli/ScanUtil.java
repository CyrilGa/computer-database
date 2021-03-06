package fr.cgaiton611.cdb.cli;

import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import fr.cgaiton611.cdb.util.ConvertUtil;
import fr.cgaiton611.cdb.validation.Type;
import fr.cgaiton611.cdb.validation.TypeValidator;

/**
 * Ask, verify, cast and return the user input.
 * 
 * @author cyril
 * @version 1.0
 */
public class ScanUtil {

	Scanner scanner = new Scanner(System.in);
	TypeValidator typeValidator = new TypeValidator();
	PrintUtil printUtil = new PrintUtil();
	ConvertUtil convertUtil = new ConvertUtil();

	/**
	 * Ask the user the give a certain input (here: an Integer)
	 * 
	 * @param msg  The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @param t    Generic variable used to test the type of T
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Optional<String> askSomething(String msg, boolean skip, Type type) {
		String skipMsg = "";
		if (skip)
			skipMsg = "(enter to skip)";
		printUtil.print(msg + " " + skipMsg + " : ");
		String input = getLine();
		if ("".equals(input) && skip)
			return Optional.empty();
		int cnt = 0;
		int max_cnt = 3;
		while (cnt++ < max_cnt) {
			if ("e".equals(input))
				return Optional.empty();
			else if (typeValidator.isType(input, type)) {
				return Optional.of(input);
			}
			if (cnt == max_cnt)
				break;
			System.out.println(msg + " must be an " + type.toString() + " (e for exit)");
			System.out.printf(msg + " : ");
			input = getLine();
		}
		System.out.println("exit...");
		return Optional.empty();
	}

	/**
	 * Ask the user the give a certain input (here: an Integer)
	 * 
	 * @param msg  The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Optional<Long> askLong(String msg, boolean skip) {
		return convertUtil.stringToLong(askSomething(msg, skip, Type.Long));
	}

	/**
	 * Ask the user the give a certain input (here: a Date)
	 * 
	 * @param msg  The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Optional<Date> askDate(String msg, boolean skip) {
		return convertUtil.stringToDate(askSomething(msg, skip, Type.Date));
	}

	/**
	 * Ask the user the give a certain input (here: an String)
	 * 
	 * @param msg  The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Optional<String> askString(String msg, boolean skip) {
		return askSomething(msg, skip, Type.String);
	}

	public String getLine() {
		return scanner.nextLine();
	}

	public void closeScanner() {
		scanner.close();
	}

}
