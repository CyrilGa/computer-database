package fr.cgaiton611.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.EmptyResultSetException;
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

	public void create() {
		try {
			computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
	}

	@Test
	public void find() {
		Computer c1;
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		Computer c2;
		try {
			c2 = computerDAO.find(c1);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
		assertEquals(c1, c2);
	}

	@Test
	public void findFail() {
		try {
			computerDAO.find(new Computer(665169595));
			fail("sql error");
		} catch (EmptyResultSetException e) {
			// ok
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
	}

	@Test
	public void update() {
		Computer c1;
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			computerDAO.update(new Computer(c1.getId(), "modified", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		Computer c2;
		try {
			c2 = computerDAO.find(c1);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
		assertEquals("modified", c2.getName());
	}

	@Test
	public void updateFail() {
		try {
			computerDAO.update(new Computer(61618451, "modified", null, null, new Company()));
			fail("sql error");
		} catch (EmptyResultSetException e) {
			// ok
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

	}

	@Test
	public void delete() {
		Computer c1;
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			computerDAO.delete(c1);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			computerDAO.delete(c1);
		} catch (EmptyResultSetException e) {

		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			computerDAO.find(c1);
		} catch (EmptyResultSetException e) {
			// ok
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
	}

	@Test
	public void deleteFail() {
		Computer c1;
		try {
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", null, null, new Company()));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			computerDAO.delete(c1);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			computerDAO.find(c1);
			fail("sql error");
		} catch (EmptyResultSetException e) {
			// ok
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
	}

}
