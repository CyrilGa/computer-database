package fr.cgaiton611.cli;

import fr.cgaiton611.cli.util.Type;

/**
 * Service that validate the data in terms of type and format
 * 
 * @author cyril
 * @version 1.0
 */
public class TypeValidator {

	public <T> boolean isType(String s, Type type) {
		if (type == Type.Long)
			return isInteger(s);
		else if (type == Type.Date)
			return isDate(s);
		else if (type == Type.String)
			return isString(s);
		else
			return false;
	}

	/**
	 * Test if a string represent an integer
	 * 
	 * @param s The string tested
	 * @return True or false if the string is an integer or not
	 */
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Test if a string represent an Timestamp
	 * 
	 * @param s The string tested
	 * @return True or false if the string is a Timestamp or not
	 */
	public boolean isDate(String s) {
		if (s.length() != 16)
			return false;
		if (! s.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d"))
			return false;
		return true;
	}

	public boolean isString(String s) {
		return true;
	}

}
