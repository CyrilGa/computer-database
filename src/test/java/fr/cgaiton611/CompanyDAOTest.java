package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;

class CompanyDAOTest {
	
	CompanyDAO companyDAO = CompanyDAO.getInstance();

	@Test
	void createAdnFind() {
		Optional<Company> c1 = companyDAO.create(new Company("test company"));
		assertTrue(c1.isPresent());
		Optional<Company> c2 = companyDAO.find(c1.get());
		assertTrue(c2.isPresent());
		assertEquals(c1.get(), c2.get());
	}

	@Test
	void update() {
		Optional<Company> c1 = companyDAO.create(new Company("test company"));
		assertTrue(c1.isPresent());
		companyDAO.update(new Company(c1.get().getId(), "modified"));
		c1 = companyDAO.find(c1.get());
		assertTrue(c1.isPresent());
		assertEquals("modified", c1.get().getName());
	}

	@Test
	void delete() {
		Optional<Company> c1 = companyDAO.create(new Company("test company"));
		assertTrue(c1.isPresent());
		companyDAO.delete(c1.get());
		c1 = companyDAO.find(c1.get());
		assertTrue(! c1.isPresent());
		assertEquals(null, c1.orElse(null));
	}

}
