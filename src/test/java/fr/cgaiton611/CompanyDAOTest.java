package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;


class CompanyDAOTest {
	CompanyDAO companyDAO  = new CompanyDAO();
	
	@Test
	void find() {
		Company company = companyDAO.find(new Company(1));
		assertNotEquals(company.getName(), "");
	}
	
	@Test
	void create() {
		Company c1 = companyDAO.create(new Company("test company"));
		Company c2 = companyDAO.find(c1);
		assertEquals(c1, c2);
	}
	
	@Test
	void update() {
		Company c1 = companyDAO.create(new Company("test company"));
		companyDAO.update(new Company(c1.getId(), "modified"));
		c1 = companyDAO.find(c1);
		assertEquals("modified", c1.getName());
	}
	
	@Test
	void delete() {
		Company c1 = companyDAO.create(new Company("test company"));
		companyDAO.delete(c1);
		c1 = companyDAO.find(c1);
		assertEquals(null, c1);
	}

}
