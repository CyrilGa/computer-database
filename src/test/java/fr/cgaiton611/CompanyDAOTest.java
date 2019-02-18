package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;

class CompanyDAOTest {
	CompanyDAO companyDAO = CompanyDAO.getInstance();

	@Test
	void find() {
		Optional<Company> c1 = companyDAO.find(new Company(1));
		if (! c1.isPresent())
			fail();
		assertNotEquals(c1.get().getName(), "");
	}

	@Test
	void create() {
		Optional<Company> c1 = companyDAO.create(new Company("test company"));
		if (! c1.isPresent())
			fail();
		Optional<Company> c2 = companyDAO.find(c1.get());
		if (! c2.isPresent())
			fail();
		assertEquals(c1.get(), c2.get());
	}

	@Test
	void update() {
		Optional<Company> c1 = companyDAO.create(new Company("test company"));
		if (! c1.isPresent())
			fail();
		companyDAO.update(new Company(c1.get().getId(), "modified"));
		c1 = companyDAO.find(c1.get());
		if (! c1.isPresent())
			fail();
		assertEquals("modified", c1.get().getName());
	}

	@Test
	void delete() {
		Optional<Company> c1 = companyDAO.create(new Company("test company"));
		if (! c1.isPresent())
			fail();
		companyDAO.delete(c1.get());
		c1 = companyDAO.find(c1.get());
		if (! c1.isPresent())
			fail();
		assertEquals(null, c1.get());
	}

}
