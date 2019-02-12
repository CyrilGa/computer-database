package fr.cgaiton611.validation;

public class Validator {
	
	public boolean isInt(String s) {
		boolean b = true;
		try {
			Integer.parseInt(s);
		}
		catch (Exception e) {
			b = false;
		}
		return b;
	}
}
