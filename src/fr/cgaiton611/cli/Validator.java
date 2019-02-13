package fr.cgaiton611.cli;

/**
 * Service that validate the data in terms of type and format
 * @author cyril
 * @version 1.0
 */
public class Validator {
	
	/**
	 * Test if a string represent an integer
	 * @param s The string tested
	 * @return True or false if the string is an integer or not
	 */
	public boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Test if a string represent an Timestamp
	 * @param s The string tested
	 * @return True or false if the string is an Timestamp or not
	 */
	public boolean isTimestamp(String s) {
		if (s.length()<16) return false;
		if (! s.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d")) return false;
		String year = s.substring(0, 4);
		String month = s.substring(5, 7);
		String day = s.substring(8, 10);
		String hour = s.substring(11, 13);
		String minute = s.substring(14, 16);
		if (isInt(year) 
				&& isInt(month)
				&& isInt(day)
				&& isInt(hour)
				&& isInt(minute)){
			if (isIn(year, -1900, 9999)
					&& isIn(month, 0, 11)
					&& isIn(day, 1, 31)
					&& isIn(hour, 0, 59)
					&& isIn(minute, 0, 59)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test if a String (converted to an integer) if included between two int
	 * @param s The string tested
	 * @param c1 The "inferior" integer
	 * @param c2 The "superior" integer
	 * @return True or false if s between c1 and c2 or not
	 */
	public boolean isIn(String s, int c1, int c2) {
		return c1 <= Integer.parseInt(s) && Integer.parseInt(s) <= c2;
	}
}
