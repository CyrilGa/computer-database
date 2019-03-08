package fr.cgaiton611.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.exception.DAOException;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.persistence.ComputerDAO;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ComputerDAOTest {

	private final Logger logger = LoggerFactory.getLogger(ComputerDAOTest.class);
	
	@Autowired
	ComputerDAO computerDAO;

	@Test
	public void createAndFind() {
		Optional<Computer> c1 = Optional.empty();
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		Optional<Computer> c2 = Optional.empty();
		try {
			c2 = computerDAO.find(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c2.isPresent());
		assertEquals(c1.get(), c2.get());
	}

	@Test
	public void update() {
		Optional<Computer> c1 = Optional.empty();
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		try {
			computerDAO.update(new Computer(c1.get().getId(), "modified", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		try {
			c1 = computerDAO.find(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		assertEquals("modified", c1.get().getName());
	}

	@Test
	public void delete() {
		Optional<Computer> c1 = Optional.empty();
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		try {
			computerDAO.delete(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		try {
			c1 = computerDAO.find(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(!c1.isPresent());
		assertEquals(null, c1.orElse(null));
	}

}
