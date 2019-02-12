package fr.cgaiton611.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.persistence.CompanyDAO;


class CompanyDAOTest {
	CompanyDAO companyDAO  = new CompanyDAO();
	
	@BeforeClass
	void init() {
	}
	
	@Test
	void find() {
		System.out.println("FIND");
		Company company = companyDAO.find(new Company(1));
		assertNotEquals(company.getName(), "");
		System.out.println("FIN FIND");
	}
	
	@Test
	void create() {
		System.out.println("CREATE");
		Company c1 = companyDAO.create(new Company("test company"));
		Company c2 = companyDAO.find(c1);
		assertEquals(c1, c2);
		System.out.println("FIN CREATE");
	}
	
	@Test
	void update() {
		System.out.println("UPDATE");
		Company c1 = companyDAO.create(new Company("test company"));
		companyDAO.update(new Company(c1.getId(), "modified"));
		Company c2 = companyDAO.find(c1);
		assertEquals("modified", c2.getName());
		System.out.println("FIN UPDATE");
	}
	
	@Test
	void delete() {
		System.out.println("DELETE");
		Company c1 = companyDAO.create(new Company("test company"));
		companyDAO.delete(c1);
		System.out.println(c1.getId());
		Company c2 = companyDAO.find(c1);
		
		Company c3 = companyDAO.find(c1);
		System.out.println(c2.getId());
		System.out.println(c2.getName());

		System.out.println(c3.getId());
		System.out.println(c3.getName());
		assertNotEquals("", c2.getName());
		System.out.println("FIN DELETE");
	}

}
