package fr.cgaiton611.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.cgaiton611.exception.validation.ValidationException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.util.ConvertUtil;

public class ComputerValidatorTest {

	ConvertUtil convertUtil = new ConvertUtil();

	@Test
	public void validateForAdd() {
		Computer computer = null;
		try {
			ComputerValidator.validateForAdd(computer);
			fail("validation failed");
		} catch (ValidationException e) {
			// ok
		}
		computer = new Computer("", null, null, null);
		try {
			ComputerValidator.validateForAdd(computer);
			fail("validation failed");
		} catch (ValidationException e) {
			// ok
		}
		computer = new Computer("", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		try {
			ComputerValidator.validateForAdd(computer);
			fail("validation failed");
		} catch (ValidationException e) {
			// ok
		}
		computer = new Computer("nom", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		try {
			ComputerValidator.validateForAdd(computer);
		} catch (ValidationException e) {
			fail("validation failed");
		}
	}

	@Test
	public void validateForEdit() {
		Computer computer = null;
		try {
			ComputerValidator.validateForAdd(computer);
			fail("validation failed");
		} catch (ValidationException e) {
			// ok
		}
		computer = new Computer("", null, null, null);
		try {
			ComputerValidator.validateForAdd(computer);
			fail("validation failed");
		} catch (ValidationException e) {
			// ok
		}
		computer = new Computer("", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		try {
			ComputerValidator.validateForAdd(computer);
			fail("validation failed");
		} catch (ValidationException e) {
			// ok
		}
		computer = new Computer("nom", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		try {
			ComputerValidator.validateForAdd(computer);
		} catch (ValidationException e) {
			fail("validation failed");
		}
		computer = new Computer(1, "nom", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		try {
			ComputerValidator.validateForAdd(computer);
		} catch (ValidationException e) {
			fail("validation failed");
		}
	}

}
