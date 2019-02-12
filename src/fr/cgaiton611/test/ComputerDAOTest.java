package fr.cgaiton611.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;

class ComputerDAOTest {

	ComputerDAO computerDAO  = new ComputerDAO();
	
	
	@Test
	void find() {
		Computer computer = computerDAO.find(new Computer(1));
		assertNotEquals(computer.getName(), "");
	}
	
	@Test
	void create() {
		Computer c1 = computerDAO.create(new Computer("test computer", null, null, 1));
		Computer c2 = computerDAO.find(c1);
		assertEquals(c1, c2);
	}
	
	@Test
	void update() {
		Computer c1 = computerDAO.create(new Computer("test computer", null, null, 1));
		computerDAO.update(new Computer(c1.getId(), "modified", null, null, 1));
		c1 = computerDAO.find(c1);
		assertEquals("modified", c1.getName());
	}
	
	@Test
	void delete() {
		Computer c1 = computerDAO.create(new Computer("test computer", null, null, 1));
		computerDAO.delete(c1);
		c1 = computerDAO.find(c1);
		assertEquals(null, c1);
	}


}
