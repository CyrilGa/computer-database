package fr.cgaiton611.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.cdb.config.HibernateConfig;
import fr.cgaiton611.cdb.dao.ComputerDAO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.NoResultRowException;
import fr.cgaiton611.cdb.exception.NoRowUpdatedException;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class})
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
			c1 = computerDAO.create(new Computer("TEST COMPUTER DAO", new Date(), new Date(), new Company()));
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
			fail("fail finFail");
		} catch(NoResultRowException e) {
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
		} catch (NoRowUpdatedException e) {
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
			computerDAO.find(c1);
			fail("fail delete");
		} catch (NoResultRowException e) {
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
			computerDAO.delete(c1);
			fail("sql error");
		} catch (NoRowUpdatedException e) {
			// ok
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
	}

}
