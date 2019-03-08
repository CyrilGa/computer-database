package fr.cgaiton611.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import fr.cgaiton611.persistence.CompanyDAO;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class CompanyDAOTest {

	private final Logger logger = LoggerFactory.getLogger(CompanyDAOTest.class);
	
	@Autowired
	CompanyDAO companyDAO;

	@Test
	public void createAndFind() {
		Optional<Company> c1 = Optional.empty();
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		Optional<Company> c2 = Optional.empty();;
		try {
			c2 = companyDAO.find(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c2.isPresent());
		assertEquals(c1.get(), c2.get());
	}

	@Test
	public void update() {
		Optional<Company> c1 = Optional.empty();
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		
		try {
			companyDAO.update(new Company(c1.get().getId(), "modified"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		try {
			c1 = companyDAO.find(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		assertEquals("modified", c1.get().getName());
	}

	@Test
	public void delete() {
		Optional<Company> c1 =Optional.empty();
		try {
			c1 = companyDAO.create(new Company("TEST COMPANY DAO"));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertTrue(c1.isPresent());
		try {
			companyDAO.delete(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		
		try {
			c1 = companyDAO.find(c1.get());
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertFalse(c1.isPresent());
		assertEquals(null, c1.orElse(null));
	}

}
