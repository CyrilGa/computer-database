package fr.cgaiton611;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
class ComputerDAOTest {

	@Autowired
	ComputerDAO computerDAO;

	@Test
	void createAndFind() {
		Optional<Computer> c1 = computerDAO.create(new Computer("test computer", null, null, new Company(1)));
		assertTrue(c1.isPresent());
		Optional<Computer> c2 = computerDAO.find(c1.get());
		assertTrue(c2.isPresent());
		assertEquals(c1.get(), c2.get());
	}

	@Test
	void update() {
		Optional<Computer> c1 = computerDAO.create(new Computer("test computer", null, null, new Company(1)));
		assertTrue(c1.isPresent());
		computerDAO.update(new Computer(c1.get().getId(), "modified", null, null, new Company(1)));
		c1 = computerDAO.find(c1.get());
		assertTrue(c1.isPresent());
		assertEquals("modified", c1.get().getName());
	}

	@Test
	void delete() {
		Optional<Computer> c1 = computerDAO.create(new Computer("test computer", null, null, new Company(1)));
		assertTrue(c1.isPresent());
		computerDAO.delete(c1.get());
		c1 = computerDAO.find(c1.get());
		assertTrue(! c1.isPresent());
		assertEquals(null, c1.orElse(null));
	}

}
