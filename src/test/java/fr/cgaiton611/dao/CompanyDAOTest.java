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
import fr.cgaiton611.persistence.CompanyDAO;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class CompanyDAOTest {

	private final Logger logger = LoggerFactory.getLogger(CompanyDAOTest.class);

	@Autowired
	CompanyDAO companyDAO;

	@Test
	public void create() {
		try {
			companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}
	}

	@Test
	public void find() {
		Company c1;
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		Company c2;
		try {
			c2 = companyDAO.find(new Company(c1.getId()));
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
			companyDAO.find(new Company(99999999));
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
		Company c1;
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			companyDAO.update(new Company(c1.getId(), "modified"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		Company c2;
		try {
			c2 = companyDAO.find(c1);
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
			companyDAO.update(new Company(56465156, "modified"));
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
		Company c1;
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			companyDAO.delete(c1);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			companyDAO.find(c1);
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
		Company c1;
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			companyDAO.delete(c1);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			fail("database error");
			return;
		}

		try {
			companyDAO.delete(c1);
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
