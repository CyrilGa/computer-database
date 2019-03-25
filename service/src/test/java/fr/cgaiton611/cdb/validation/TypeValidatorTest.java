package fr.cgaiton611.cdb.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class TypeValidatorTest {

	TypeValidator typeValidator = new TypeValidator();

	@Test
	public void isTypeString() {
		String s = "";
		assertFalse(typeValidator.isType(s, Type.Date));
		assertFalse(typeValidator.isType(s, Type.Long));
		assertTrue(typeValidator.isType(s, Type.String));
	}

	@Test
	public void isTypeStringAndLong() {
		String s = "10";
		assertFalse(typeValidator.isType(s, Type.Date));
		assertTrue(typeValidator.isType(s, Type.Long));
		assertTrue(typeValidator.isType(s, Type.String));
	}

	@Test
	public void isTypeStringAndDate() {
		String s = "2012-02-02 22:10";
		assertTrue(typeValidator.isType(s, Type.Date));
		assertFalse(typeValidator.isType(s, Type.Long));
		assertTrue(typeValidator.isType(s, Type.String));
	}

}
