package fr.cgaiton611.cdb.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.cgaiton611.cdb.exception.validation.ComputerNullException;
import fr.cgaiton611.cdb.exception.validation.DateNotValidException;
import fr.cgaiton611.cdb.exception.validation.IdIsZeroException;
import fr.cgaiton611.cdb.exception.validation.NameIsEmptyException;
import fr.cgaiton611.cdb.exception.validation.ValidationException;
import fr.cgaiton611.cdb.model.Computer;

/**
 * Validator for all field of class Computer
 * 
 * @author cyril
 *
 */

public class ComputerValidator {

	public static void validateForAdd(Computer computer) throws ValidationException {
		if (computer == null) {
			throw new ComputerNullException();
		}
		stringNotEmpty(computer.getName());
		validateDate(computer.getIntroduced());
		validateDate(computer.getDiscontinued());
	}

	public static void validateForEdit(Computer computer) throws ValidationException {
		if (computer == null) {
			throw new ComputerNullException();
		}
		longNotZero(computer.getId());
		stringNotEmpty(computer.getName());
		validateDate(computer.getIntroduced());
		validateDate(computer.getDiscontinued());
	}

	private static void stringNotEmpty(String s) throws NameIsEmptyException {
		if(s == null || s == "") {
			throw new NameIsEmptyException();
		}
	}

	private static void longNotZero(long i) throws IdIsZeroException {
		if(i == 0l) {
			throw new IdIsZeroException();
		}
	}

	/**
	 * Validate the format a Date : yyyy-MM-dd hh:mm
	 * 
	 * @param date
	 * @return
	 * @throws DateNotValidException 
	 * @throws ComputerValidatorException
	 */
	private static void validateDate(Date date) throws DateNotValidException {
		if (date == null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String s = dateFormat.format(date);

			String year = s.substring(0, 4);
			String month = s.substring(5, 7);
			String day = s.substring(8, 10);
			String hour = s.substring(11, 13);
			String minute = s.substring(14, 16);
			boolean goodFormatDate = isIn(year, 0000, 9999) && isIn(month, 0, 11) && isIn(day, 1, 31) && isIn(hour, 0, 23)
					&& isIn(minute, 0, 59);
			if (!goodFormatDate) {
				throw new DateNotValidException();
			}
		}
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
		} catch (NumberFormatException e) {
			return false;
		}
		return c1 <= test && test <= c2;
	}
}
