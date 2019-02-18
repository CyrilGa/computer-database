package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.cli.TypeValidator;

class TypeValidatorTest {
	TypeValidator validator = new TypeValidator();

	@Test
	void isInt() {
		String s1 = "33N";
		String s2 = "N33";
		String s3 = "3N3";
		String s4 = "33";
		assertFalse(validator.isInteger(s1));
		assertFalse(validator.isInteger(s2));
		assertFalse(validator.isInteger(s3));
		assertTrue(validator.isInteger(s4));
	}

	@Test
	void isTimestamp() {
		String s1 = "2012:00:01 22:22";
		String s2 = "2012-00-01 32:22";
		String s3 = "0010-01-01 22:22";
		assertFalse(validator.isDate(s1));
		assertFalse(validator.isDate(s2));
		assertTrue(validator.isDate(s3));
	}

}
