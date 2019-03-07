package fr.cgaiton611.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.cgaiton611.model.Computer;
import fr.cgaiton611.util.ConvertUtil;

public class ComputerValidatorTest {
	
	ConvertUtil convertUtil = new ConvertUtil();
	
	@Test
	public void validate() {
		Computer computer = null;
		assertFalse(ComputerValidator.validate(computer));
		computer = new Computer("", null, null, null);
		assertTrue(ComputerValidator.validate(computer));
		computer = new Computer("", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		assertTrue(ComputerValidator.validate(computer));
	}
	
	@Test
	public void validateForAdd() {
		Computer computer = null;
		assertFalse(ComputerValidator.validateForAdd(computer));
		computer = new Computer("", null, null, null);
		assertFalse(ComputerValidator.validateForAdd(computer));
		computer = new Computer("", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		assertFalse(ComputerValidator.validateForAdd(computer));
		computer = new Computer("nom", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		assertTrue(ComputerValidator.validateForAdd(computer));
	}
	
	@Test
	public void validateForEdit() {
		Computer computer = null;
		assertFalse(ComputerValidator.validateForEdit(computer));
		computer = new Computer("", null, null, null);
		assertFalse(ComputerValidator.validateForEdit(computer));
		computer = new Computer("", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		assertFalse(ComputerValidator.validateForEdit(computer));
		computer = new Computer("nom", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		assertFalse(ComputerValidator.validateForEdit(computer));
		computer = new Computer(1, "nom", convertUtil.stringToDate("2012-02-02 22:22").get(), null, null);
		assertTrue(ComputerValidator.validateForEdit(computer));
	}
	

}
