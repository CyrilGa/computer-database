package fr.cgaiton611.cli;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Service that ask, verify, cast and return the user input.
 * @author cyril
 * @version 1.0
 */
public class ScanUtil {

	Scanner scanner = new Scanner(System.in);
	Validator validator = new Validator();

	/**
	 * Ask the user the give a certain input (here: an Integer)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Integer askInteger(String msg, boolean skip) {
		String skipMsg = "";
		if (skip) skipMsg = "(enter to skip)";
		System.out.printf(msg+" "+skipMsg+" : ");
		String input = scanner.nextLine();
		if ("".equals(input) && skip) return null;
		Integer integer;
		int cnt = 0;
		int max_cnt = 3;
		while (cnt++ < max_cnt) {
			if ("e".equals(input)) return null;
			else if (validator.isInt(input)) {
				integer = Integer.parseInt(input);
				return integer;
			}
			if (cnt == max_cnt) break;
			System.out.println(msg+" must be an integer (e for exit)");
			System.out.printf(msg+" : ");
			input = scanner.nextLine();
		}
		System.out.println("exit...");
		return null;
	}
	
	/**
	 * Ask the user the give a certain input (here: an Timestamp)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public Timestamp askTimestamp(String msg, boolean skip) {
		String skipMsg = "";
		if (skip) skipMsg = "(enter to skip)";
		System.out.printf(msg+" <yyyy-MM-dd hh:mm> "+skipMsg+" : ");
		String input = scanner.nextLine();
		if ("".equals(input) && skip) return null;
		Timestamp timestamp;
		int cnt = 0;
		int max_cnt = 3;
		while (cnt++ < max_cnt) {
			if ("e".equals(input)) return null;
			else if (validator.isTimestamp(input)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
				Date parsedDate;
				try {
					parsedDate = dateFormat.parse(input+":00:000");
					timestamp = new Timestamp(parsedDate.getTime());
					return timestamp;
				} catch (ParseException e) {
					System.out.println("exception");
				}
			}
			if (cnt == max_cnt) break;
			System.out.println(msg+" must respect year(-1900-9999) month(0-11) day(0-31) (e for exit)");
			System.out.printf(msg+" <yyyy-MM-dd hh:mm> : ");
			input = scanner.nextLine();
		}
		System.out.println("exit...");
		return null;
	}
	
	/**
	 * Ask the user the give a certain input (here: an String)
	 * @param msg The entry asked to the user
	 * @param skip Define if the user can skip the asked entry
	 * @return An integer or null if user has skip or the 3 tries were failed
	 */
	public String askString(String msg, boolean skip) {
		String skipMsg = "";
		if (skip) skipMsg = "(enter to skip)";
		System.out.printf(msg+" "+skipMsg+" : ");
		String input = scanner.nextLine();
		if ("".equals(input) && skip) return null;
		
		int cnt = 0;
		int max_cnt = 3;
		while (cnt++ < max_cnt) {
			if ("e".equals(input)) return null;
			else if (! input.equals("")) {
				return input;
			}
			if (cnt == max_cnt) break;
			System.out.println(msg+" must not be empty");
			System.out.printf(msg+" : ");
			input = scanner.nextLine();
		}
		System.out.println("exit...");
		return null;
	}

}
