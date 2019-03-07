package fr.cgaiton611.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.cgaiton611.model.Computer;

/**
 * Validator for all field of class Computer
 * 
 * @author cyril
 *
 */
public class ComputerValidator {
	public static boolean validate(Computer computer) {
		if (computer==null)
			return false;
		return validateDate(computer.getIntroduced()) && validateDate(computer.getDiscontinued());
	}
	
	public static boolean validateForAdd(Computer computer) {
		return stringNotEmpty(computer.getName())
				&& validateDate(computer.getIntroduced())
				&& validateDate(computer.getDiscontinued());
	}
	
	public static boolean validateForEdit(Computer computer) {
		return longNotZero(computer.getId())
				&& stringNotEmpty(computer.getName())
				&& validateDate(computer.getIntroduced())
				&& validateDate(computer.getDiscontinued());
	}
	
	private static boolean stringNotEmpty(String s) {
		return s!=null && s!="";
	}
	
	private static boolean longNotZero(long i) {
		return i!=0;
	}

	/**
	 * Validate the format a Date : yyyy-MM-dd hh:mm
	 * 
	 * @param date
	 * @return
	 * @throws ComputerValidatorException
	 */
	private static boolean validateDate(Date date) {
		if (date == null)
			return true;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String s = dateFormat.format(date);

		String year = s.substring(0, 4);
		String month = s.substring(5, 7);
		String day = s.substring(8, 10);
		String hour = s.substring(11, 13);
		String minute = s.substring(14, 16);
		if (isIn(year, 0000, 9999) && isIn(month, 0, 11) && isIn(day, 1, 31) && isIn(hour, 0, 23)
				&& isIn(minute, 0, 59)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Test if a String (converted to an integer) if included between two integer
	 * 
	 * @param s  The string tested
	 * @param c1 The "inferior" integer
	 * @param c2 The "superior" integer
	 * @return True or false if s between c1 and c2 or not
	 */
	private static boolean isIn(String s, int c1, int c2) {
		int test;
		try {
			test = Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return c1 <= test && test <= c2;
	}
}