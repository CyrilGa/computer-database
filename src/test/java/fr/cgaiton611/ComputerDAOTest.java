package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;

class ComputerDAOTest {

	ComputerDAO computerDAO = ComputerDAO.getInstance();

	@Test
	void find() {
		Optional<Computer> c1 = computerDAO.find(new Computer(1));
		if (! c1.isPresent())
			fail();
		assertNotEquals(c1.get().getName(), "");
	}

	@Test
	void create() {
		Optional<Computer> c1 = computerDAO.create(new Computer("test computer", null, null, 1));
		if (! c1.isPresent())
			fail();
		Optional<Computer> c2 = computerDAO.find(c1.get());
		if (! c2.isPresent())
			fail();
		assertEquals(c1.get(), c2.get());
	}

	@Test
	void update() {
		Optional<Computer> c1 = computerDAO.create(new Computer("test computer", null, null, 1));
		if (! c1.isPresent())
			fail();
		computerDAO.update(new Computer(c1.get().getId(), "modified", null, null, 1));
		c1 = computerDAO.find(c1.get());
		if (! c1.isPresent())
			fail();
		assertEquals("modified", c1.get().getName());
	}

	@Test
	void delete() {
		Optional<Computer> c1 = computerDAO.create(new Computer("test computer", null, null, 1));
		if (! c1.isPresent())
			fail();
		computerDAO.delete(c1.get());
		c1 = computerDAO.find(c1.get());
		if (! c1.isPresent())
			fail();
		assertEquals(null, c1);
	}

}
